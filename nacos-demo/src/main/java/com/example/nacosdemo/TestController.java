package com.example.nacosdemo;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author jiaoguanping
 * @version 1.0.0
 * @ClassName TestController
 * @date 2020/8/18  20:16
 */
@RestController
public class TestController {

    @Value(value = "${file.name}")
    private String name;


    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("nacos")
    public String getNacos() {
        return name;
    }

    @GetMapping("getRpcNacos")
    public String getRpcNacos() {
        restTemplate.getForObject("http://server1/test", String.class);
        return name;
    }
}
