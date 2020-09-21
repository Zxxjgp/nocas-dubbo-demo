package com.guice.cn.helloworddemo;

import com.guice.cn.MyApplet;

/**
 * @author jiaoguanping
 * @version 1.0.0
 * @ClassName HelloWordPrinter
 * @date 2020/9/10  0:33
 */
public class HelloWordPrinter implements MyApplet {
    private void printHelloWord() {
        System.out.println("hello word");
    }

    public void run() {
        printHelloWord();
    }
}
