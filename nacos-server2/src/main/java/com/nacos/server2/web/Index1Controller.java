package com.nacos.server2.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiaoguanping
 * @version 1.0.0
 * @ClassName IndexController
 * @date 2020/9/7  21:00
 */
@RestController
@RequestMapping("index")
public class Index1Controller {

    @GetMapping("test")
    public String get() {
        return "01";
    }
}
