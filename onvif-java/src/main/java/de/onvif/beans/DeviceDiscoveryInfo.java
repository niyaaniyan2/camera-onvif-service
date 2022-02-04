package de.onvif.beans;

public class DeviceDiscoveryInfo implements Comparable<DeviceDiscoveryInfo> {

    private String address;
    private String types;
    private String scopes;
    private String xAddrs;

    @Override
    public int compareTo(DeviceDiscoveryInfo o) {
        if (this.address == null && o.address == null) {
            return 0;
        } else if (this.address == null) {
            return -1;
        } else if (o.address == null) {
            return 1;
        } else {
            return this.address.compareTo(o.address);
        }
    }

    public String getAddress() {
        return address == null ? "" : address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void addAddress(String address) {
        if (this.address == null) {
            setAddress(address);
        } else {
            this.address += "; " + address;
        }
    }

    public String getTypes() {
        return types == null ? "" : types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public void addTypes(String types) {
        if (this.types == null) {
            setTypes(types);
        } else {
            this.types += "; " + types;
        }
    }

    public String getScopes() {
        return scopes == null ? "" : scopes;
    }

    public void setScopes(String scopes) {
        this.scopes = scopes;
    }

    public void addScopes(String scopes) {
        if (this.scopes == null) {
            setScopes(scopes);
        } else {
            this.scopes += "; " + scopes;
        }
    }

    public String getXAddrs() {
        return xAddrs == null ? "" : xAddrs;
    }

    public void setXAddrs(String xAddrs) {
        this.xAddrs = xAddrs;
    }

    public void addXAddrs(String xAddrs) {
        if (this.xAddrs == null) {
            setXAddrs(xAddrs);
        } else {
            this.xAddrs += "; " + xAddrs;
        }
    }
}
