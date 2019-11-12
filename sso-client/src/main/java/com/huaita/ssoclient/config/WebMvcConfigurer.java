package com.huaita.ssoclient.config;

import com.huaita.ssoclient.interceptor.SessionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 这个打jar的时候不需要
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /**
         * 拦截器按照顺序执行
         */
        final String[] notLoginInterceptPaths = {"/static/**","/admin/login","/error/**","/login"};
        registry.addInterceptor(new SessionInterceptor()).addPathPatterns("/**")
                .excludePathPatterns(notLoginInterceptPaths);;
//        registry.addInterceptor(new SessionInterceptor()).addPathPatterns("/**")
//                .addPathPatterns("/one/**");


        super.addInterceptors(registry);
    }

}