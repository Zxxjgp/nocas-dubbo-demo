package com.nacos.api.config;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author jiaoguanping
 * @version 1.0.0
 * @ClassName JgpFilter
 * @date 2020/9/3  11:02
 */
@Component
@RefreshScope
public class JgpFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest build = exchange.getRequest().mutate().header("userId", "10086")
                .build();
        ServerWebExchange build1 = exchange.mutate().request(build).build();
        return chain.filter(build1);
    }
}
