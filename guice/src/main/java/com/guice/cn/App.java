package com.guice.cn;

import com.guice.cn.helloworddemo.Configuration;

/**
 * @author jiaoguanping
 * @version 1.0.0
 * @ClassName App
 * @date 2020/9/10  0:25
 */
public class App {
    public static void main(String[] args) {
        MyApplet applent = Configuration.getMainApplent();
        applent.run();
    }
}
