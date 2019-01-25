package com.nk.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@SpringBootApplication
public class ZuulProxyServerApplication {
	
	  public static void main(String[] args) {
	        SpringApplication.run(ZuulProxyServerApplication.class, args);
	    }
	  
/*	  @Bean
	  public CorsFilter corsFilter() {
	      final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	      final CorsConfiguration config = new CorsConfiguration();
	      config.setAllowCredentials(true);
	      config.addAllowedOrigin("*");
	      config.addAllowedHeader("*");
	      config.addAllowedMethod("OPTIONS");
	      config.addAllowedMethod("HEAD");
	      config.addAllowedMethod("GET");
	      config.addAllowedMethod("PUT");
	      config.addAllowedMethod("POST");
	      config.addAllowedMethod("DELETE");
	      config.addAllowedMethod("PATCH");
	      source.registerCorsConfiguration("/**", config);
	      return new CorsFilter(source);
	  }*/
	  
	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
			.route("hello", r -> r.path("/hello")
				.uri("http://localhost:8090/hello"))
			.route("ticks", r->r.path("/ticks")
			    .uri("http://localhost:8090/ticks"))
				.build();
	}
}

