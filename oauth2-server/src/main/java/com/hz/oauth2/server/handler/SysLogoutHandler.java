package com.hz.oauth2.server.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: authority-management-sys
 * @author: zgr
 * @create: 2021-07-29 10:46
 **/
@Configuration
@Slf4j
public class SysLogoutHandler implements LogoutHandler {

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        //是用户登出具体逻辑的实现,可以记录用户下线的时间，ip，以下为删除token的逻辑


    }
}
