package com.hz.oauth2.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: authority-management-sys-2.0
 * @author: zgr
 * @create: 2021-08-04 16:07
 **/
@Configuration
public class SysMvcConfig implements WebMvcConfigurer {
    /**
     * 配置登录页面的视图信息
     */

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("login").setViewName("login");
    }
}
