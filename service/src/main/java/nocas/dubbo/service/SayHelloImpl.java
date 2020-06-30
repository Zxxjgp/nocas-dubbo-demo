package nocas.dubbo.service;

import nocas.dubbo.api.SayHello;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author jiaoguanping
 * @version 1.0.0
 * @ClassName SayHelloImpl
 * @date 2020/6/30  11:31
 */
@Service(version = "1.0.0")
public class SayHelloImpl implements SayHello {
    @Override
    public String sayHello(String str) {
        return "hello一号" + str;
    }
}
