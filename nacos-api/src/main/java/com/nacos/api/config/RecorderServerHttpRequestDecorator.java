//package com.nacos.api.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
//import reactor.core.publisher.Flux;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * <b>Description:</b>
// * @Auther: jiaoguanping
// * @Date: 2020/9/21 14:29
// */
//@Slf4j
//public class RecorderServerHttpRequestDecorator extends ServerHttpRequestDecorator {
//
//    private final List<DataBuffer> dataBuffers = new ArrayList<>();
//
//    public RecorderServerHttpRequestDecorator(ServerHttpRequest delegate) {
//        super(delegate);
//        super.getBody().map(dataBuffer -> {
//            dataBuffers.add(dataBuffer);
//            log.info("**************************************************");
//            return dataBuffer;
//        }).subscribe();
//    }
//
//    @Override
//    public Flux<DataBuffer> getBody() {
//        log.info("********************getBody***********************");
//        return copy();
//    }
//
//    private Flux<DataBuffer> copy() {
//        log.info("****************copy************************");
//        return Flux.fromIterable(dataBuffers)
//                .map(buf -> buf.factory().wrap(buf.asByteBuffer()));
//    }
//
//
//}
