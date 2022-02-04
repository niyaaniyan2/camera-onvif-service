package com.stengg.onvif.controllers;

import com.stengg.onvif.exceptions.InvalidRequestException;
import com.stengg.onvif.models.CameraAuthInfo;
import com.stengg.onvif.models.CameraDeviceInfo;
import com.stengg.onvif.models.CameraDiscoveryInfo;
import com.stengg.onvif.services.CameraService;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.xml.soap.SOAPException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CameraControllerTests {

    @Mock
    private CameraService cameraService;

    @InjectMocks
    private CameraController cameraController;

    private List<CameraDiscoveryInfo> cameraDiscoveryInfoList;
    private CameraDiscoveryInfo cameraDiscoveryInfo;
    private CameraAuthInfo cameraAuthInfo;
    private CameraAuthInfo errCameraAuthInfo;
    private CameraDeviceInfo cameraDeviceInfo;
    private String rtspUrl;
    private ArrayList<String> rtspUrls;

    @Before
    public void init(){
        cameraDiscoveryInfo = new CameraDiscoveryInfo();
        cameraDiscoveryInfoList = new ArrayList<CameraDiscoveryInfo>();
        cameraDiscoveryInfo.setUuid("fec88000-f31d-11b4-839b-849a4085e11f");
        cameraDiscoveryInfo.addIp("10.0.43.154");
        cameraDiscoveryInfo.addScope("onvif://www.onvif.org/hardware/DS-2CD51C5G0-IZS");
        cameraDiscoveryInfo.addType("Device");
        cameraDiscoveryInfoList.add(cameraDiscoveryInfo);

        cameraAuthInfo = new CameraAuthInfo();
        cameraAuthInfo.setIpAddress("10.0.43.154");
        cameraAuthInfo.setUserName("admin");
        cameraAuthInfo.setPassword("admin6388");

        errCameraAuthInfo = new CameraAuthInfo();
        errCameraAuthInfo.setIpAddress("10.0.0.0");

        cameraDeviceInfo = new CameraDeviceInfo();
        cameraDeviceInfo.setIpAddress("10.0.43.154");
        cameraDeviceInfo.setSerialNumber("5J093F0PAG34ECA");
        cameraDeviceInfo.setManufacturer("General");
        cameraDeviceInfo.setModel("IP Camera");
        cameraDeviceInfo.setBuildDate("2019-06-19");
        cameraDeviceInfo.setHardwareId("1.00");
        cameraDeviceInfo.setFirmwareVersion("2.622.0000000.7.R");
        rtspUrl = "rtsp://10.0.43.154:554/cam/realmonitor?channel=1&subtype=0&unicast=true&proto=Onvif";
        rtspUrls = new ArrayList<String>();
        rtspUrls.add(rtspUrl);
        cameraDeviceInfo.setRtspUrls(rtspUrls);
        cameraDeviceInfo.setEncoding("H264");

    }

    @Test
    public void discovery_isSuccess(){
        Mockito.when(cameraService.discoverCameras()).thenReturn(cameraDiscoveryInfoList);
        List<CameraDiscoveryInfo> cameraDiscoveryInfoResponse = cameraController.discovery();

        Assert.assertEquals(cameraDiscoveryInfoList.size(),cameraDiscoveryInfoResponse.size());
        Assert.assertEquals(cameraDiscoveryInfoList.get(0).getIps(),cameraDiscoveryInfoResponse.get(0).getIps());
        Assert.assertEquals(cameraDiscoveryInfoList.get(0).getUuid(),cameraDiscoveryInfoResponse.get(0).getUuid());
        Assert.assertEquals(cameraDiscoveryInfoList.get(0).getScopes(),cameraDiscoveryInfoResponse.get(0).getScopes());
        Assert.assertEquals(cameraDiscoveryInfoList.get(0).getTypes(),cameraDiscoveryInfoResponse.get(0).getTypes());
    }

    @Test
    public void getCameraInfo_isSuccess() throws Exception {
        Mockito.when(cameraService.getCameraInfo(cameraAuthInfo)).thenReturn(cameraDeviceInfo);
        CameraDeviceInfo cameraDeviceInfoResponse = cameraController.getCameraInfo(cameraAuthInfo);

        Assert.assertEquals(cameraDeviceInfo.getIpAddress(),cameraDeviceInfoResponse.getIpAddress());
        Assert.assertEquals(cameraDeviceInfo.getSerialNumber(),cameraDeviceInfoResponse.getSerialNumber());
        Assert.assertEquals(cameraDeviceInfo.getFirmwareVersion(),cameraDeviceInfoResponse.getFirmwareVersion());
        Assert.assertEquals(cameraDeviceInfo.getManufacturer(),cameraDeviceInfoResponse.getManufacturer());
        Assert.assertEquals(cameraDeviceInfo.getModel(),cameraDeviceInfoResponse.getModel());
        Assert.assertEquals(cameraDeviceInfo.getBuildDate(),cameraDeviceInfoResponse.getBuildDate());
        Assert.assertEquals(cameraDeviceInfo.getHardwareId(),cameraDeviceInfoResponse.getHardwareId());
        Assert.assertEquals(cameraDeviceInfo.getEncoding(),cameraDeviceInfoResponse.getEncoding());
        Assert.assertEquals(cameraDeviceInfo.getRtspUrls(),cameraDeviceInfoResponse.getRtspUrls());
    }

    @Test
    public void getCameraInfo_validationError() throws Exception {
        Mockito.when(cameraService.getCameraInfo(errCameraAuthInfo)).thenThrow( new InvalidRequestException("Invalid IP Address"));
        CameraDeviceInfo cameraDeviceInfoResponse = cameraController.getCameraInfo(cameraAuthInfo);

        Assert.assertNull(cameraDeviceInfoResponse);
    }
}
