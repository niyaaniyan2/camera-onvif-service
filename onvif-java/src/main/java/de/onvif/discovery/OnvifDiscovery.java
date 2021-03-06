package de.onvif.discovery;

import de.onvif.beans.DeviceDiscoveryInfo;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.soap.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.*;

public class OnvifDiscovery {

    public static final String WS_DISCOVERY_SOAP_VERSION = "SOAP 1.2 Protocol";
    public static final String WS_DISCOVERY_CONTENT_TYPE = "application/soap+xml";
    public static final int WS_DISCOVERY_TIMEOUT = 10 * 1000;//millisecond
    public static final int WS_DISCOVERY_PORT = 3702;
    public static final String WS_DISCOVERY_ADDRESS_IPv4 = "239.255.255.250";
    /**
     * IPv6 not supported yet. set enableIPv6 to true for testing if you need IP6 discovery.
     */
    public static final boolean enableIPv6 = false;
    public static final String WS_DISCOVERY_ADDRESS_IPv6 = "[FF02::C]";
    public static final String WS_DISCOVERY_PROBE_MESSAGE =
            "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" " +
                    "xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2004/08/addressing\" " +
                    "xmlns:tns=\"http://schemas.xmlsoap.org/ws/2005/04/discovery\">" +
                    "<soap:Header>" +
                    "<wsa:Action>http://schemas.xmlsoap.org/ws/2005/04/discovery/Probe</wsa:Action>" +
                    "<wsa:MessageID>urn:uuid:xxx</wsa:MessageID>" +
                    "<wsa:To>urn:schemas-xmlsoap-org:ws:2005:04:discovery</wsa:To>" +
                    "</soap:Header>" +
                    "<soap:Body><tns:Probe/></soap:Body>" +
                    "</soap:Envelope>";
    private static final Random random = new SecureRandom();

    public static Collection<URL> discoverOnvifURLs() {
        return DeviceDiscovery.discoverWsDevicesAsUrls("^http$", ".*onvif.*");
    }

    public static Collection<DeviceDiscoveryInfo> discoverWsDevices() {
        final Collection<DeviceDiscoveryInfo> discoveredDevices = new ConcurrentSkipListSet<>();
        final CountDownLatch serverStarted = new CountDownLatch(1);
        final CountDownLatch serverFinished = new CountDownLatch(1);
        final Collection<InetAddress> addressList = new ArrayList<>();

        try {
            final Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface anInterface = interfaces.nextElement();
                if (!anInterface.isLoopback()) {
                    final List<InterfaceAddress> interfaceAddresses = anInterface.getInterfaceAddresses();
                    for (InterfaceAddress address : interfaceAddresses) {
                        Class<? extends InetAddress> clz = address.getAddress().getClass();
                        if (!enableIPv6 && address.getAddress() instanceof Inet6Address)
                            continue;
                        addressList.add(address.getAddress());
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        ExecutorService executorService = Executors.newCachedThreadPool();
        for (final InetAddress address : addressList) {
            Runnable runnable =
                    () -> {
                        try {
                            final String uuid = UUID.randomUUID().toString();
                            final String probe =
                                    WS_DISCOVERY_PROBE_MESSAGE.replaceAll(
                                            "<wsa:MessageID>urn:uuid:.*</wsa:MessageID>",
                                            "<wsa:MessageID>urn:uuid:" + uuid + "</wsa:MessageID>");
                            final int port = random.nextInt(20000) + 40000;
                            @SuppressWarnings("SocketOpenedButNotSafelyClosed") final DatagramSocket server = new DatagramSocket(port, address);
                            new Thread(() -> {
                                try {
                                    final DatagramPacket packet = new DatagramPacket(new byte[4096], 4096);
                                    server.setSoTimeout(WS_DISCOVERY_TIMEOUT);
                                    long timerStarted = System.currentTimeMillis();
                                    while (System.currentTimeMillis() - timerStarted < (WS_DISCOVERY_TIMEOUT)) {
                                        serverStarted.countDown();
                                        server.receive(packet);
                                        DeviceDiscoveryInfo discoveredDevice =
                                                parseSoapResponse(Arrays.copyOf(packet.getData(), packet.getLength()));
                                        discoveredDevices.add(discoveredDevice);
                                    }
                                } catch (SocketTimeoutException ignored) {
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    serverFinished.countDown();
                                    server.close();
                                }
                            }).start();
                            try {
                                serverStarted.await(1000, TimeUnit.MILLISECONDS);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (address instanceof Inet4Address) {
                                server.send(
                                        new DatagramPacket(
                                                probe.getBytes(StandardCharsets.UTF_8),
                                                probe.length(),
                                                InetAddress.getByName(WS_DISCOVERY_ADDRESS_IPv4),
                                                WS_DISCOVERY_PORT));
                            } else {
                                if (address instanceof Inet6Address) {
                                    if (enableIPv6)
                                        server.send(
                                                new DatagramPacket(
                                                        probe.getBytes(StandardCharsets.UTF_8),
                                                        probe.length(),
                                                        InetAddress.getByName(WS_DISCOVERY_ADDRESS_IPv6),
                                                        WS_DISCOVERY_PORT));
                                } else {
                                    assert (false); // 	unknown network type.. ignore or warn developer
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            serverFinished.await((WS_DISCOVERY_TIMEOUT), TimeUnit.MILLISECONDS);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    };
            executorService.submit(runnable);
        }
        try {
            executorService.shutdown();
            executorService.awaitTermination(WS_DISCOVERY_TIMEOUT + 2000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ignored) {
        }
        return discoveredDevices;
    }

    private static Collection<Node> getNodeMatching(Node body, String regexp) {
        final Collection<Node> nodes = new ArrayList<>();
        if (body.getNodeName().matches(regexp))
            nodes.add(body);
        if (body.getChildNodes().getLength() == 0)
            return nodes;
        NodeList returnList = body.getChildNodes();
        for (int k = 0; k < returnList.getLength(); k++) {
            final Node node = returnList.item(k);
            nodes.addAll(getNodeMatching(node, regexp));
        }
        return nodes;
    }

    private static DeviceDiscoveryInfo parseSoapResponse(byte[] data)
            throws SOAPException, IOException {
        //System.out.println("Response:\n" + new String(data));
        DeviceDiscoveryInfo deviceDiscoveryInfo = new DeviceDiscoveryInfo();
        MessageFactory factory = MessageFactory.newInstance(WS_DISCOVERY_SOAP_VERSION);
        final MimeHeaders headers = new MimeHeaders();
        headers.addHeader("Content-type", WS_DISCOVERY_CONTENT_TYPE);
        SOAPMessage message = factory.createMessage(headers, new ByteArrayInputStream(data));
        SOAPBody body = message.getSOAPBody();
        for (Node node : getNodeMatching(body, ".*:Address")) {
            deviceDiscoveryInfo.addAddress(node.getTextContent());
        }
        for (Node node : getNodeMatching(body, ".*:Types")) {
            deviceDiscoveryInfo.addTypes(node.getTextContent());
        }
        for (Node node : getNodeMatching(body, ".*:Scopes")) {
            deviceDiscoveryInfo.addScopes(node.getTextContent());
        }
        for (Node node : getNodeMatching(body, ".*:XAddrs")) {
            deviceDiscoveryInfo.addXAddrs(node.getTextContent());
        }
        return deviceDiscoveryInfo;
    }
}
