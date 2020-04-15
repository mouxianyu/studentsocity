package com.mouxianyu.studentsociety.common.config;

import com.mouxianyu.studentsociety.common.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description: TODO
 * @author: kingsme@yeah.net
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**")
                .excludePathPatterns(
                        "/client/toLogin",
                        "/client/login",
                        "/client/logout",
                        "/client/forgetPassword",
                        "/user/toLogin",
                        "/user/login",
                        "/user/logout",
                        "/img/**",
                        "/sendMail",
                        "/checkMailCode",
                        "/forgetPassword",
                        "/favicon.ico",
                        "/client/css/**",
                        "/client/js/**",
                        "/my/**",
                        "/public/**",
                        "/vendor/**",
                        "/bootstrap/**");
    }
}
