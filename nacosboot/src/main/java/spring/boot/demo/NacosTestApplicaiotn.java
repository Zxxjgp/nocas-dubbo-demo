package spring.boot.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author jiaoguanping
 * @version 1.0.0
 * @ClassName NacosTestApplicaiotn
 * @date 2020/8/18  23:46
 */
@SpringBootApplication
public class NacosTestApplicaiotn {

    @Value("${spring.application.name}")
    private String applicationName;

    public static void main(String[] args) {
            SpringApplication.run(NacosTestApplicaiotn.class, args);

    }
}
