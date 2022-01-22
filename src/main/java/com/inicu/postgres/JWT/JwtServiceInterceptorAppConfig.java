package com.inicu.postgres.JWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
public class JwtServiceInterceptorAppConfig extends WebMvcConfigurerAdapter {

 @Autowired
 JwtServiceInterceptor JwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(JwtInterceptor);
    }
}
