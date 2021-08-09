package com.hz.oauth2.server.aspect;


import com.hz.oauth2.server.constant.ResultCode;
import com.hz.oauth2.server.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;


/**
 * @author honorzhang
 */
@Slf4j
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        StringBuilder msg = new StringBuilder("访问: ").append(httpServletRequest.getRequestURI());
        // 用户登录时身份认证未通过
        if (e instanceof BadCredentialsException) {
            log.info("{} 身份认证失败,无效的凭据", msg.toString());
            ResultUtil.writeResponse(httpServletResponse, ResultCode.UN_AUTHORIZED, e.getMessage());
        } else if (e instanceof InsufficientAuthenticationException) {
            log.info("{} 身份认证失败,token缺失或无效", msg.toString());
            ResultUtil.writeResponse(httpServletResponse, ResultCode.UN_AUTHORIZED, e.getMessage());
        } else {
            log.info("{} 身份认证失败,用户token无效.", msg.toString());
            ResultUtil.writeResponse(httpServletResponse, ResultCode.UN_AUTHORIZED, e.getMessage());
        }

    }
}
