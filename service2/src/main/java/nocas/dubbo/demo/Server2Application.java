package nocas.dubbo.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author jiaoguanping
 * @version 1.0.0
 * @ClassName Server2Application
 * @date 2020/6/30  16:29
 */
@EnableDiscoveryClient
@SpringBootApplication
public class Server2Application {
    public static void main(String[] args) {
        SpringApplication.run(Server2Application.class, args);

    }
}
