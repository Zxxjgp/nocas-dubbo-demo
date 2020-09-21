package com.nacos.api.test;

/**
 * @author jiaoguanping
 * @version 1.0.0
 * @ClassName ThreadTest
 * @date 2020/9/20  23:21
 */
public class ThreadTest {


    String var = "0";
    public void main(String[] args) {

        Thread B = new Thread(()->{
            // 主线程调用B.start()之前
            // 所有对共享变量的修改，此处皆可见
            // 此例中，var==77
            System.out.println(this.var);
        });
// 此处对共享变量var修改
        this.var = "77";
// 主线程启动子线程
        B.start();
    }
}
