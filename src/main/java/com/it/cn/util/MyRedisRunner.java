package com.it.cn.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 项目启动  自动启动redis服务端
 */
@Component
public class MyRedisRunner implements CommandLineRunner {

    @Value("${redis.url}")
    private String url;

    @Override
    public void run(String... args) {
        System.out.println("quickly start redis");
        try {
            Runtime.getRuntime().exec("cmd   /c   "+url);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
