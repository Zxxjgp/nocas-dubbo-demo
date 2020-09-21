package com.nacos.api.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author jiaoguanping
 * @version 1.0.0
 * @ClassName AirGatewayFilterFactory
 * @date 2020/9/21  18:42
 */
@Component
@Order(1)
@Slf4j
public class AirGatewayFilterFactory extends AbstractGatewayFilterFactory<AirGatewayFilterFactory.Config> {

    private static String WITHPARAMS = "withParams";


    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(WITHPARAMS);
    }

    private final List<HttpMessageReader<?>> messageReaders;

    public AirGatewayFilterFactory() {
        super(Config.class);
        this.messageReaders = HandlerStrategies.withDefaults().messageReaders();
    }

    public AirGatewayFilterFactory(
            List<HttpMessageReader<?>> messageReaders) {
        this.messageReaders = messageReaders;
    }

    ServerHttpRequestDecorator decorate(ServerWebExchange exchange, HttpHeaders headers,
                                        CachedBodyOutputMessage outputMessage) {
        return new ServerHttpRequestDecorator(exchange.getRequest()) {
            @Override
            public HttpHeaders getHeaders() {
                long contentLength = headers.getContentLength();
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.putAll(headers);
                if (contentLength > 0) {
                    httpHeaders.setContentLength(contentLength);
                }
                else {
                    // TODO: this causes a 'HTTP/1.1 411 Length Required' // on
                    // httpbin.org
                    httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
                }
                return httpHeaders;
            }

            @Override
            public Flux<DataBuffer> getBody() {
                log.info("---------------------------------");
                return outputMessage.getBody();
            }
        };
    }
    @Override
    public GatewayFilter apply(Config config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                ServerHttpRequest request = exchange.getRequest();
                ServerHttpResponse response = exchange.getResponse();

                String accessToken = request.getHeaders().getFirst("accessToken");
                if (!StringUtils.hasLength(accessToken)) {
                    throw new IllegalArgumentException("accessToken");
                }
                ServerRequest serverRequest = ServerRequest.create(exchange,
                        messageReaders);

                // TODO: flux or mono
                //重点
                AtomicReference<String> atomicReference = new AtomicReference<>();
                Mono<?> modifiedBody = serverRequest.bodyToMono(String.class)
                        .flatMap(originalBody -> {
                            atomicReference.set(originalBody);
                            log.info("-=--==-=-=-==-={}", originalBody);
                            return Mono.just(originalBody);});
                HttpHeaders headers = new HttpHeaders();
                headers.putAll(exchange.getRequest().getHeaders());

                // the new content type will be computed by bodyInserter
                // and then set in the request decorator
                headers.remove(HttpHeaders.CONTENT_LENGTH);

                CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(
                        exchange, headers);

                Class outClass = String.class;
                BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody, outClass);

                return bodyInserter.insert(outputMessage, new BodyInserterContext())
                        // .log("modify_request", Level.INFO)
                        .then(Mono.defer(() -> {
                            ServerHttpRequest decorator = decorate(exchange, headers,
                                    outputMessage);
                            String requestBody = atomicReference.get();
                            if (org.apache.commons.lang3.StringUtils.isBlank(requestBody)) {
                                return authErro(response, "402","请求体为空！");
                            }
                            String airResponse = getAirResponse(requestBody);

                            if (org.apache.commons.lang3.StringUtils.isBlank(airResponse)) {
                                return authErro(response, "402",  "请求处理错误");
                            }
                            if ("N".equals(request.getHeaders().get(""))) {
                                return chain
                                        .filter(exchange.mutate().request(decorator).build());
                            }
                            return exchange.getResponse().writeWith(Flux.create(s -> {
                                DataBuffer wrap = exchange.getResponse().bufferFactory().wrap(airResponse.getBytes());
                                s.next(wrap);
                                s.complete();
                            }));
                        }));
            }

        };
    }

    public static class Config {

        private String withParams;

        public String isWithParams() {
            return withParams;
        }

        public void setWithParams(String withParams) {
            this.withParams = withParams;
        }

    }

    private Mono<Void> authErro(ServerHttpResponse resp, String code, String msg) {
        resp.setStatusCode(HttpStatus.UNAUTHORIZED);

        HttpHeaders httpHeaders = resp.getHeaders();
        httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
        httpHeaders.add("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        String warningStr = "{\"code\":" + code + ",\"msg\":" + msg + "}";
        DataBuffer bodyDataBuffer = resp.bufferFactory().wrap(warningStr.getBytes());
        return resp.writeWith(Mono.just(bodyDataBuffer));
    }



    private String getAirResponse(String body) {

        return "{\"name\":\"111\"}";
    }
}
