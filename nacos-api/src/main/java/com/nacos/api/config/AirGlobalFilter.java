package com.nacos.api.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author jiaoguanping
 * @version 1.0.0
 * @ClassName AirGlobalFilter
 * @date 2020/9/3  11:25
 */
@Configuration
@Slf4j
public class AirGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("你来了啊 ");
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
