package com.techshop.orderservice.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class FeignClientInterceptor implements RequestInterceptor {

    private static final String jwtSecret = "thisismysecretbuiminhhuy2001";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    public static String getTokenHeader() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
    }

    public static Claims getClaims() {
        try {
            Claims body = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(getTokenHeader()).getBody();
            return body;
        } catch (Exception e) {
            System.out.println(e.getMessage() + " => " + e);
        }
        return null;
    }
    @Override
    public void apply(RequestTemplate requestTemplate) {

        requestTemplate.header(AUTHORIZATION_HEADER, getTokenHeader());

    }
}