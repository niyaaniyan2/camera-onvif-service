package com.stengg.onvif.controllers;

import com.stengg.onvif.models.CameraAuthInfo;
import com.stengg.onvif.models.CameraDeviceInfo;
import com.stengg.onvif.models.CameraDiscoveryInfo;
import com.stengg.onvif.services.CameraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/camera")
public class CameraController {

    @Autowired
    private CameraService cameraService;

    @GetMapping("/discovery")
    public List<CameraDiscoveryInfo> discovery() {
        return cameraService.discoverCameras();
    }

    @PostMapping("/info")
    public CameraDeviceInfo getCameraInfo(@RequestBody CameraAuthInfo cameraAuthInfo) throws Exception {
        if (cameraAuthInfo == null)
            throw new Exception("Input camera info is null");
        try {
            return cameraService.getCameraInfo(cameraAuthInfo);
        } catch (Exception ex) {
            throw ex;
        }
    }
}
