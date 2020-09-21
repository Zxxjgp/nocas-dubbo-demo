/*
package com.nacos.api.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.StripPrefixGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;

import org.springframework.http.HttpStatus;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.StringUtils;

import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


@Slf4j
@Configuration
public class TestGlobalFilter implements GlobalFilter, Ordered {


    private final List<HttpMessageReader<?>> messageReaders;

    public TestGlobalFilter() {
        this.messageReaders = HandlerStrategies.withDefaults().messageReaders();
    }

    public TestGlobalFilter(
            List<HttpMessageReader<?>> messageReaders) {
        this.messageReaders = messageReaders;
    }

    @Deprecated
    public TestGlobalFilter(ServerCodecConfigurer codecConfigurer) {
        this(codecConfigurer.getReaders());
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
                    if ("1".equals(request.getHeaders().get(""))) {
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

    @Override
    public int getOrder() {
        return 0;
    }


}
*/
