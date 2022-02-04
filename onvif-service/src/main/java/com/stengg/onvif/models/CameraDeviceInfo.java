package com.stengg.onvif.models;

import java.util.ArrayList;

public class CameraDeviceInfo {
    private String ipAddress;
    private String serialNumber;
    private String manufacturer;
    private String model;
    private String firmwareVersion;
    private String buildDate;
    private String hardwareId;
    private String encoding;
    private ArrayList<String> rtspUrls;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public String getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(String buildDate) {
        this.buildDate = buildDate;
    }

    public String getHardwareId() {
        return hardwareId;
    }

    public void setHardwareId(String hardwareId) {
        this.hardwareId = hardwareId;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public ArrayList<String> getRtspUrls() {
        return rtspUrls;
    }

    public void setRtspUrls(ArrayList<String> rtspUrls) {
        this.rtspUrls = rtspUrls;
    }

}
