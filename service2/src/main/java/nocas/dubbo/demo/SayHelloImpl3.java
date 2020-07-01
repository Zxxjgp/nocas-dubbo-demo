package nocas.dubbo.demo;

import nocas.dubbo.api.SayHello;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author jiaoguanping
 * @version 1.0.0
 * @date 2020/6/30  18:49
 */
@Service(group = "sayHelloImpl3")
public class SayHelloImpl3 implements SayHello {

    @Override
    public String sayHello(String str) {
        return "hello 三号" + str;
    }
}
