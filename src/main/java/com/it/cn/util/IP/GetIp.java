package com.it.cn.util.IP;

import java.net.Inet4Address;
import java.net.UnknownHostException;

public class GetIp {
    public static String getip(){
        String ip = null;
        try {
            Inet4Address address = (Inet4Address) Inet4Address.getLocalHost();
            ip = address.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ip;
    }
}
