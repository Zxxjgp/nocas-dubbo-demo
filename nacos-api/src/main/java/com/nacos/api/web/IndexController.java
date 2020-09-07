package com.nacos.api.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author jiaoguanping
 * @version 1.0.0
 * @ClassName IndexController
 * @date 2020/9/2  14:16
 */
@RestController
@RequestMapping("index1")
public class IndexController {

    @GetMapping("test")
    public Mono<String> get(@RequestHeader("userId") String userId) {
        return Mono.just("111111" + userId);
    }
}
