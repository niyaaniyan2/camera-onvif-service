package com.stengg.onvif.services;

import com.stengg.onvif.exceptions.InvalidRequestException;
import com.stengg.onvif.models.CameraAuthInfo;
import com.stengg.onvif.models.CameraDeviceInfo;
import com.stengg.onvif.models.CameraDiscoveryInfo;
import com.stengg.onvif.utils.CameraOnvifUtil;
import de.onvif.beans.DeviceDiscoveryInfo;
import de.onvif.discovery.OnvifDiscovery;
import de.onvif.soap.OnvifDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.soap.SOAPException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CameraOnvifImpl implements CameraService {

    private static final Pattern ONVIF_ADDRESS_PATTERN = Pattern.compile("(urn:)?(uuid:)?(.*)");
    private static final Pattern ONVIF_TYPE_PATTERN = Pattern.compile("(dn|tds|wsdp|pub):(\\w*)");
    private static final Pattern ONVIF_XADDRS_PATTERN =
            Pattern.compile("(http|https)://(\\d+.\\d+.\\d+.\\d+)(/.*)?");
    private OnvifDevice onvifDevice;
    private CameraDeviceInfo cameraDeviceInfo;
    @Autowired
    private CameraOnvifUtil onvifUtil;

    @Override
    public List<CameraDiscoveryInfo> discoverCameras() {
        List<CameraDiscoveryInfo> cameras = new ArrayList<>();
        Collection<DeviceDiscoveryInfo> discoveredDevices = OnvifDiscovery.discoverWsDevices();
        for (DeviceDiscoveryInfo discoveredDevice : discoveredDevices) {
            CameraDiscoveryInfo camera = new CameraDiscoveryInfo();
            String address = discoveredDevice.getAddress().trim();
            Matcher matcher = ONVIF_ADDRESS_PATTERN.matcher(address);
            if (matcher.matches()) {
                camera.setUuid(matcher.group(3));
            }
            String scopes = discoveredDevice.getScopes();
            for (String scope : scopes.split(" ")) {
                camera.addScope(scope);
            }
            String types = discoveredDevice.getTypes();
            for (String type : types.split(" ")) {
                matcher = ONVIF_TYPE_PATTERN.matcher(type);
                if (matcher.matches()) {
                    camera.addType(matcher.group(2));
                }
            }
            String xAddrs = discoveredDevice.getXAddrs();
            for (String x : xAddrs.split(" ")) {
                matcher = ONVIF_XADDRS_PATTERN.matcher(x);
                if (matcher.matches()) {
                    camera.addIp(matcher.group(2));
                }
            }
            cameras.add(camera);
        }
        return cameras;
    }

    @Override
    public CameraDeviceInfo getCameraInfo(CameraAuthInfo cameraAuthInfo) throws MalformedURLException, SOAPException, ConnectException {
        onvifUtil.validateRequestEntity(cameraAuthInfo);
        if(!cameraAuthInfo.getUserName().isEmpty() && !cameraAuthInfo.getPassword().isEmpty())
            onvifDevice = new OnvifDevice(cameraAuthInfo.getIpAddress(), cameraAuthInfo.getUserName(), cameraAuthInfo.getPassword());
        else
            onvifDevice = new OnvifDevice(cameraAuthInfo.getIpAddress());
        cameraDeviceInfo = new CameraDeviceInfo();
        cameraDeviceInfo.setIpAddress(cameraAuthInfo.getIpAddress());
        onvifDevice = new OnvifDevice(cameraAuthInfo.getIpAddress(), cameraAuthInfo.getUserName(), cameraAuthInfo.getPassword());
        cameraDeviceInfo = new CameraDeviceInfo();
        onvifUtil.addCameraDeviceInfoToEntity(onvifDevice.getDeviceInfo(), cameraDeviceInfo);
        onvifUtil.addStreamInfoToEntity(onvifDevice.getStreamUris(), cameraDeviceInfo);
        onvifUtil.addEncodingToEntity(onvifDevice.getDeviceCodec(),cameraDeviceInfo);
        return cameraDeviceInfo;
    }
}
