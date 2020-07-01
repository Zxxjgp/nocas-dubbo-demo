package nocas.dubbo.web.controller;

import nocas.dubbo.api.PathService;
import nocas.dubbo.api.SayHello;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiaoguanping
 * @version 1.0.0
 * @date 2020/6/30  15:25
 */
@RestController
@RequestMapping("test")
public class SayHelloController {



    @Reference(group = "sayHelloImpl3")
    private SayHello sayHelloImpl3;


    @Reference(version = "1.0.0")
    private PathService pathService;

    @GetMapping("hello3/{str}")
    public String hello3(@PathVariable("str") String str) {
        return sayHelloImpl3.sayHello(str);
    }


    @GetMapping("path1/{str}")
    public String path1(@PathVariable("str") String str) {
        return pathService.headers("a", "b", 10);
    }

}

