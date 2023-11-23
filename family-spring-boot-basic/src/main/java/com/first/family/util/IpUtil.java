package com.first.family.util;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @description:
 * @author: cuiweiman
 * @date: 2023/11/7 15:04
 */
@Slf4j
public class IpUtil {

    public static String getLocalIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.error("IpUtil#getLocalIp error, {}", e.getMessage());
            return "localhost";
        }
    }

}
