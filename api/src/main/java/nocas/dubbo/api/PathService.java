package nocas.dubbo.api;

/**
 * @author jiaoguanping
 * @version 1.0.0
 * @ClassName PathService
 * @date 2020/7/1  13:46
 */
public interface PathService {

    String param(String param);

    String params(int a, String b);

    String headers(String header, String header2, Integer param);
}
