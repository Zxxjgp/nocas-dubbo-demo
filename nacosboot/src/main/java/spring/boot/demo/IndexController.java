package spring.boot.demo;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiaoguanping
 * @version 1.0.0
 * @ClassName IndexController
 * @date 2020/8/18  23:51
 */
@RestController
public class IndexController {

    @NacosValue(value = "${spring.application.name}", autoRefreshed = true)
    private String name;

    @GetMapping("test")
    public String tegye() {
        return name;
    }
}
