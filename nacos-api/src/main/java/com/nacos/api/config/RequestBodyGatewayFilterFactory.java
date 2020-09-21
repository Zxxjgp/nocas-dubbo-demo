///*
// * Copyright 2013-2019 the original author or authors.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      https://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package com.nacos.api.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
//import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction;
//import org.springframework.cloud.gateway.support.BodyInserterContext;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.Ordered;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.codec.HttpMessageReader;
//import org.springframework.http.codec.ServerCodecConfigurer;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
//import org.springframework.web.reactive.function.BodyInserter;
//import org.springframework.web.reactive.function.BodyInserters;
//import org.springframework.web.reactive.function.server.HandlerStrategies;
//import org.springframework.web.reactive.function.server.ServerRequest;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//import java.util.Map;
//
//
///**
// * GatewayFilter that modifies the request body.
// */
//@Slf4j
//@Configuration
//public class RequestBodyGatewayFilterFactory implements GlobalFilter, Ordered {
//
//	private final List<HttpMessageReader<?>> messageReaders;
//
//	public RequestBodyGatewayFilterFactory() {
//		this.messageReaders = HandlerStrategies.withDefaults().messageReaders();
//	}
//
//	public RequestBodyGatewayFilterFactory(
//			List<HttpMessageReader<?>> messageReaders) {
//		this.messageReaders = messageReaders;
//	}
//
//	@Deprecated
//	public RequestBodyGatewayFilterFactory(ServerCodecConfigurer codecConfigurer) {
//		this(codecConfigurer.getReaders());
//	}
//
//
//	ServerHttpRequestDecorator decorate(ServerWebExchange exchange, HttpHeaders headers,
//			CachedBodyOutputMessage outputMessage) {
//		return new ServerHttpRequestDecorator(exchange.getRequest()) {
//			@Override
//			public HttpHeaders getHeaders() {
//				long contentLength = headers.getContentLength();
//				HttpHeaders httpHeaders = new HttpHeaders();
//				httpHeaders.putAll(headers);
//				if (contentLength > 0) {
//					httpHeaders.setContentLength(contentLength);
//				}
//				else {
//					// TODO: this causes a 'HTTP/1.1 411 Length Required' // on
//					// httpbin.org
//					httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
//				}
//				return httpHeaders;
//			}
//
//			@Override
//			public Flux<DataBuffer> getBody() {
//				log.info("---------------------------------");
//				return outputMessage.getBody();
//			}
//		};
//	}
//
//	@Override
//	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//		Config config = new Config();
//		config.setInClass(String.class);
//		config.setOutClass(String.class);
//		Class inClass = String.class;
//		ServerRequest serverRequest = ServerRequest.create(exchange,
//				messageReaders);
//
//		// TODO: flux or mono
//		Mono<?> modifiedBody = serverRequest.bodyToMono(inClass)
//				.flatMap(originalBody -> {
//				    log.info("-=--==-=-=-==-={}", originalBody);
//				    return Mono.just(originalBody);});
//
//		BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody,
//				config.getOutClass());
//		HttpHeaders headers = new HttpHeaders();
//		headers.putAll(exchange.getRequest().getHeaders());
//
//		// the new content type will be computed by bodyInserter
//		// and then set in the request decorator
//		headers.remove(HttpHeaders.CONTENT_LENGTH);
//
//		// if the body is changing content types, set it here, to the bodyInserter
//		// will know about it
//		if (config.getContentType() != null) {
//			headers.set(HttpHeaders.CONTENT_TYPE, config.getContentType());
//		}
//		CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(
//				exchange, headers);
//		return bodyInserter.insert(outputMessage, new BodyInserterContext())
//				// .log("modify_request", Level.INFO)
//				.then(Mono.defer(() -> {
//					ServerHttpRequest decorator = decorate(exchange, headers,
//							outputMessage);
//					String res = getAirResponse("");
///*					return chain
//							.filter(exchange.mutate().request(decorator).build());*/
//					return exchange.getResponse().writeWith(Flux.create(s -> {
//						DataBuffer wrap = exchange.getResponse().bufferFactory().wrap(res.getBytes());
//						s.next(wrap);
//						s.complete();
//					}));
//				}));
//	}
//
//
//	private String getAirResponse(String body) {
//
//		return "{\"name\":\"111\"}";
//	}
//
//	@Override
//	public int getOrder() {
//		return 0;
//	}
//
//	public static class Config {
//
//		private Class inClass;
//
//		private Class outClass;
//
//		private String contentType;
//
//		@Deprecated
//		private Map<String, Object> inHints;
//
//		@Deprecated
//		private Map<String, Object> outHints;
//
//		private RewriteFunction rewriteFunction;
//
//		public Class getInClass() {
//			return inClass;
//		}
//
//		public Config setInClass(Class inClass) {
//			this.inClass = inClass;
//			return this;
//		}
//
//		public Class getOutClass() {
//			return outClass;
//		}
//
//		public Config setOutClass(Class outClass) {
//			this.outClass = outClass;
//			return this;
//		}
//
//		@Deprecated
//		public Map<String, Object> getInHints() {
//			return inHints;
//		}
//
//		@Deprecated
//		public Config setInHints(Map<String, Object> inHints) {
//			this.inHints = inHints;
//			return this;
//		}
//
//		@Deprecated
//		public Map<String, Object> getOutHints() {
//			return outHints;
//		}
//
//		@Deprecated
//		public Config setOutHints(Map<String, Object> outHints) {
//			this.outHints = outHints;
//			return this;
//		}
//
//		public RewriteFunction getRewriteFunction() {
//			return rewriteFunction;
//		}
//
//		public Config setRewriteFunction(RewriteFunction rewriteFunction) {
//			this.rewriteFunction = rewriteFunction;
//			return this;
//		}
//
//		public <T, R> Config setRewriteFunction(Class<T> inClass, Class<R> outClass,
//				RewriteFunction<T, R> rewriteFunction) {
//			setInClass(inClass);
//			setOutClass(outClass);
//			setRewriteFunction(rewriteFunction);
//			return this;
//		}
//
//		public String getContentType() {
//			return contentType;
//		}
//
//		public Config setContentType(String contentType) {
//			this.contentType = contentType;
//			return this;
//		}
//
//	}
//
//}
