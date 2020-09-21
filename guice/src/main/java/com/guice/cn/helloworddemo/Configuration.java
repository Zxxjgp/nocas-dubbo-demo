package com.guice.cn.helloworddemo;

/**
 * @author jiaoguanping
 * @version 1.0.0
 * @ClassName Configuration
 * @date 2020/9/10  0:38
 */
public class Configuration {

    public static HelloWordPrinter getMainApplent() {
        return new HelloWordPrinter();
    }
}
