package com.techshop.apigateway.security.filter;

import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.stereotype.Component;

@Component
public class MyFilterFactory extends
        AbstractGatewayFilterFactory<MyFilterFactory.Config> {

    public MyFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return new JwtAuthenticationFilter(); // or MyFilter(config), depending on your needs
    }

    public static class Config {
        // this can be empty if you don't need to pass any filter arguments
    }
}
