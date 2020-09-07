package com.nacos.server1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author jiaoguanping
 * @version 1.0.0
 * @ClassName Server1Application
 * @date 2020/9/2  9:00
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Server1Application {
    public static void main(String[] args) {
        SpringApplication.run(Server1Application.class, args);
    }
}
