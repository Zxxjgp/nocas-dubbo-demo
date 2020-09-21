package com.nacos.api.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyRequestBodyGatewayFilterFactory;
import org.springframework.cloud.gateway.handler.predicate.ReadBodyPredicateFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.List;

/**
 * @author jiaoguanping
 * @version 1.0.0
 * @date 2020/9/2  14:24
 */
@Component
@Order(1)
public class AuthGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthGatewayFilterFactory.Config> {

    private static String WITHPARAMS = "withParams";

    public AuthGatewayFilterFactory(){
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(WITHPARAMS);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            Object cachedBody = exchange.getAttribute("cachedRequestBodyObject");
            System.out.println(cachedBody);
            ServerHttpRequest build = exchange.getRequest().mutate().header("userId", "1234567").build();
            ServerWebExchange build1 = exchange.mutate().request(build).build();
            return chain.filter(build1);
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
}
