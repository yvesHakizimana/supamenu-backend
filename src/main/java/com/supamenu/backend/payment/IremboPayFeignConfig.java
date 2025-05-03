package com.supamenu.backend.payment;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IremboPayFeignConfig {

    private static final String SECRET_KEY = "sk_live_6bbf4b14f03844a8a8c094469221e805";

    public RequestInterceptor requestInterceptor(){
        return requestTemplate -> {
            requestTemplate.header("Content-Type", "application/json");
            requestTemplate.header("Accept", "application/json");
            requestTemplate.header("irembopay-secretkey", SECRET_KEY);
            requestTemplate.header("User-Agent", "Supamenu");
        };
    }
}
