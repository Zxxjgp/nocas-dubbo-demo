package com.nacos.server1.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiaoguanping
 * @version 1.0.0
 * @ClassName IndexController
 * @date 2020/9/7  21:03
 */
@RestController
@RequestMapping("index")
public class IndexController {

    @GetMapping("test")
    public String get() {
        return "server1";
    }
}
