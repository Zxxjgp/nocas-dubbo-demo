package nocas.dubbo.demo;

import lombok.extern.slf4j.Slf4j;
import nocas.dubbo.api.PathService;
import org.apache.dubbo.config.annotation.Service;

import javax.ws.rs.*;

/**
 * @author jiaoguanping
 * @version 1.0.0
 * @ClassName PathServiceImpl
 * @date 2020/7/1  13:46
 */
@Slf4j
@Service(version = "1.0.0")
@Path("/")
public class PathServiceImpl implements PathService {

    @Override
    @Path("param")
    @GET
    public String param(@QueryParam("param") String param) {
        log.info("/param", param);
        return param;
    }

    @Override
    @Path("params")
    @POST
    public String params(@QueryParam("a") int a, @QueryParam("b") String b) {
        log.info("/params{}", a + b);
        return a + b;
    }

    @Override
    @Path("headers")
    @GET
    public String headers(@HeaderParam("h") String header,
                          @HeaderParam("h2") String header2, @QueryParam("v") Integer param) {
        String result = header + " , " + header2 + " , " + param;
        log.info("/headers{}", result);
        return result;
    }
}
