package com.it.cn.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 项目启动  自动打开浏览器
 */
@Component   //放开的话  启动后自动弹出
public class MyCommandRunner implements CommandLineRunner {

    @Value("${server.port}")
    private String port;

    @Override
    public void run(String... args) {
        System.out.println("quickly start a page");
        try {
            Runtime.getRuntime().exec("cmd   /c   start   http://localhost:"+port);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
