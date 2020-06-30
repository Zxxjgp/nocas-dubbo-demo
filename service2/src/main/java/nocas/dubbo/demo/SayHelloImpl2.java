package nocas.dubbo.demo;

import nocas.dubbo.api.SayHello;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author jiaoguanping
 * @version 1.0.0
 * @ClassName SayHelloImpl
 * @date 2020/6/30  11:31
 */
@Service(version = "2.0.0")
public class SayHelloImpl2 implements SayHello {
    @Override
    public String sayHello(String str) {
        return "hello 二号" + str;
    }
}
