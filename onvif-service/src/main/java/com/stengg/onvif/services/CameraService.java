package com.stengg.onvif.services;

import com.stengg.onvif.models.CameraAuthInfo;
import com.stengg.onvif.models.CameraDeviceInfo;
import com.stengg.onvif.models.CameraDiscoveryInfo;
import org.springframework.stereotype.Service;

import javax.xml.soap.SOAPException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.util.List;

@Service
public interface CameraService {

    List<CameraDiscoveryInfo> discoverCameras();

    CameraDeviceInfo getCameraInfo(CameraAuthInfo cameraAuthInfo) throws MalformedURLException, SOAPException, ConnectException;
}
