package com.yue.yinterface.config;

import com.yue.yinterface.interceptor.FlowInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new FlowInterceptor()).addPathPatterns("/Internet/**").addPathPatterns("/name/**");
    }
}
