package ru.izlishki.dhtserver.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Hashtable;

//Object for each server to keep track of the parameters

@ConfigurationProperties(prefix = "server.instance")
public class ServerInstance {

    private Integer id;
    private String ipSuc;
    private String ip;
    private Hashtable<String, String> h;
    private Boolean serverOn;

    public ServerInstance() {
        try {
            this.ip = InetAddress.getLocalHost().getHostAddress();
            this.ipSuc = this.ip;
            this.h = new Hashtable<>();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIpSuc() {
        return ipSuc;
    }

    public void setIpSuc(String ipSuc) {
        this.ipSuc = ipSuc;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Hashtable<String, String> getH() {
        return h;
    }

    public void setH(String key, String value) {
        this.h.put(key,value);
    }

    public Boolean getServerOn() {
        return serverOn;
    }

    public void setServerOn(Boolean serverOn) {
        this.serverOn = serverOn;
    }
}
