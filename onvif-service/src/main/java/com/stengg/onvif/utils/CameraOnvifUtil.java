package com.stengg.onvif.utils;

import com.stengg.onvif.exceptions.InvalidRequestException;
import com.stengg.onvif.models.CameraAuthInfo;
import com.stengg.onvif.models.CameraDeviceInfo;
import de.onvif.beans.DeviceInfo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class CameraOnvifUtil {
    public void addCameraDeviceInfoToEntity(DeviceInfo deviceInfo, CameraDeviceInfo cameraDeviceInfo){
        cameraDeviceInfo.setSerialNumber(deviceInfo.getSerialNumber());
        cameraDeviceInfo.setManufacturer(deviceInfo.getManufacturer());
        cameraDeviceInfo.setModel(deviceInfo.getModel());
        cameraDeviceInfo.setHardwareId(deviceInfo.getHardwareId());
        if(deviceInfo.getFirmwareVersion().contains("Build Date")) {
        if((deviceInfo.getFirmwareVersion().split(", Build Date ")[1]) != null) {
            cameraDeviceInfo.setFirmwareVersion(deviceInfo.getFirmwareVersion().split(", Build Date ")[0]);
            cameraDeviceInfo.setBuildDate(deviceInfo.getFirmwareVersion().split(", Build Date ")[1]);
        }
        else
            cameraDeviceInfo.setFirmwareVersion(deviceInfo.getFirmwareVersion());
        }
    }

    public void addStreamInfoToEntity(ArrayList<String> streamUris, CameraDeviceInfo cameraDeviceInfo) {
        cameraDeviceInfo.setRtspUrls(streamUris);
    }

    public void validateRequestEntity(CameraAuthInfo cameraAuthInfo) {
        if (StringUtils.isEmpty(cameraAuthInfo.getIpAddress()))
            throw new InvalidRequestException("Invalid IP Address");
       /** if (StringUtils.isEmpty(cameraAuthInfo.getUserName())) //Assuming username and password can be empty
            throw new InvalidRequestException("Invalid User Name");
        if (StringUtils.isEmpty(cameraAuthInfo.getPassword()))
            throw new InvalidRequestException("Invalid Password"); **/
    }

    public void addEncodingToEntity(String encoding, CameraDeviceInfo cameraDeviceInfo) {
        if(!encoding.isEmpty())
            cameraDeviceInfo.setEncoding(encoding);
    }
}