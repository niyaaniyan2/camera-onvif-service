package com.stengg.onvif.models;

import java.util.ArrayList;
import java.util.List;

public class CameraDiscoveryInfo {

    private String uuid;
    private final List<String> scopes = new ArrayList<>();
    private final List<String> types = new ArrayList<>();
    private final List<String> ips = new ArrayList<>();

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<String> getScopes() {
        return scopes;
    }

    public void addScope(String scope) {
        this.scopes.add(scope);
    }

    public List<String> getTypes() {
        return types;
    }

    public void addType(String type) {
        this.types.add(type);
    }

    public List<String> getIps() {
        return ips;
    }

    public void addIp(String ipAddress) {
        this.ips.add(ipAddress);
    }
}
