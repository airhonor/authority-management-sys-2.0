package com.hz.oauth2.server.handler;


import com.hz.oauth2.server.constant.ResultCode;
import com.hz.oauth2.server.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: authority-management-sys
 * @author: zgr
 * @create: 2021-07-29 11:08
 **/
@Configuration
@Slf4j
public class SysLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        //登出成功的执行的逻辑
        ResultUtil.writeResponse(httpServletResponse, ResultCode.SUCCESS, ResultCode.SUCCESS.getMsg());
    }
}
