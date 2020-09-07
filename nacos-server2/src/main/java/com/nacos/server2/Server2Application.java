package com.nacos.server2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author jiaoguanping
 * @version 1.0.0
 * @date 2020/9/2  9:34
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Server2Application {
    public static void main(String[] args) {
        SpringApplication.run(Server2Application.class, args);
    }
}
