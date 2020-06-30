package nocas.dubbo.web.controller;

import lombok.extern.slf4j.Slf4j;
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

    @Reference(protocol = "dubbo", version = "1.0.0")
    private SayHello sayHello1;

    @Reference(protocol = "dubbo", version = "2.0.0")
    private SayHello sayHello2;

    @GetMapping("hello1/{str}")
    public String hello1(@PathVariable("str") String str) {
        return sayHello1.sayHello(str);
    }



    @GetMapping("hello2/{str}")
    public String hello2(@PathVariable("str") String str) {
        return sayHello2.sayHello(str);
    }


}

