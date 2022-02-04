package com.stengg.onvif.services;

import com.stengg.onvif.controllers.CameraController;
import com.stengg.onvif.models.CameraAuthInfo;
import com.stengg.onvif.models.CameraDeviceInfo;
import de.onvif.beans.DeviceInfo;
import de.onvif.soap.OnvifDevice;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.xml.soap.SOAPException;
import java.net.ConnectException;
import java.net.MalformedURLException;

@RunWith(MockitoJUnitRunner.class)
public class CameraServiceTests {
    @Mock
    private OnvifDevice onvifDevice;

    @InjectMocks
    private CameraOnvifImpl cameraService;

    private DeviceInfo deviceInfo;
    private CameraAuthInfo cameraAuthInfo;

    @Before
    public void init() throws MalformedURLException, SOAPException, ConnectException {

        cameraAuthInfo = new CameraAuthInfo();
        cameraAuthInfo.setIpAddress("10.0.43.154");
        cameraAuthInfo.setUserName("admin");
        cameraAuthInfo.setPassword("admin6388");

        onvifDevice = new OnvifDevice(cameraAuthInfo.getIpAddress(),cameraAuthInfo.getUserName(),cameraAuthInfo.getPassword());
        deviceInfo = new DeviceInfo( "General",
                "IP Camera",
                "2.622.0000000.7.R",
                "5J093F0PAG34ECA",
                "1.00");
        deviceInfo.setSerialNumber("5J093F0PAG34ECA");
        deviceInfo.setManufacturer("General");
        deviceInfo.setModel("IP Camera");
        deviceInfo.setHardwareId("1.00");
        deviceInfo.setFirmwareVersion("2.622.0000000.7.R");
    }
    @Test
    public void getCameraInfo_isSuccess() throws MalformedURLException, SOAPException, ConnectException {

        Mockito.when(onvifDevice.getDeviceInfo()).thenReturn(deviceInfo);
        CameraDeviceInfo cameraDeviceInfoResponse = cameraService.getCameraInfo(cameraAuthInfo);

        Assert.assertEquals(deviceInfo.getSerialNumber(),cameraDeviceInfoResponse.getSerialNumber());
        Assert.assertEquals(deviceInfo.getFirmwareVersion(),cameraDeviceInfoResponse.getFirmwareVersion());
        Assert.assertEquals(deviceInfo.getManufacturer(),cameraDeviceInfoResponse.getManufacturer());
        Assert.assertEquals(deviceInfo.getModel(),cameraDeviceInfoResponse.getModel());
        Assert.assertEquals(deviceInfo.getHardwareId(),cameraDeviceInfoResponse.getHardwareId());
    }

}
