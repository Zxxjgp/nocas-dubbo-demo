/**
 * @author jiaoguanping
 * @version 1.0.0
 * @ClassName Test
 * @date 2020/9/20  23:24
 */
public class Test {

    String var = "0";

    @org.junit.jupiter.api.Test
    public void ts() throws InterruptedException {

        Thread B = new Thread(()->{
            // 此处对共享变量var修改
            var = "99";
        });
        B.start();
        System.out.println(var);
        B.join();
        System.out.println(var);
    }
}
