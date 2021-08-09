package com.hz.oauth2.server.config;

import com.hz.oauth2.server.handler.SysLogoutHandler;
import com.hz.oauth2.server.handler.SysLogoutSuccessHandler;
import com.hz.oauth2.server.service.SysUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @program: authority-management-sys-2.0
 * @author: zgr
 * @create: 2021-08-03 09:55
 **/

@Configuration
@EnableWebSecurity
public class SysSecurityConfig extends WebSecurityConfigurerAdapter {


    private final SysUserService sysUserService;
    private final SysLogoutHandler sysLogoutHandler;
    private final SysLogoutSuccessHandler sysLogoutSuccessHandler;


    public SysSecurityConfig(SysUserService sysUserService, SysLogoutHandler sysLogoutHandler, SysLogoutSuccessHandler sysLogoutSuccessHandler) {
        this.sysUserService = sysUserService;
        this.sysLogoutHandler = sysLogoutHandler;
        this.sysLogoutSuccessHandler = sysLogoutSuccessHandler;
    }

    /**
     * 配置认证管理器
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    /**
     * 这里是对认证管理器的添加配置，自定义用户详情
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(sysUserService).passwordEncoder(new BCryptPasswordEncoder())
        ;
    }

    //web ignore比较适合配置前端相关的静态资源，它是完全绕过spring security的所有filter的
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resource");
    }


    /**
     * 安全请求配置,这里配置的是security的部分，这里配置全部通过，安全拦截在资源服务的配置文件中配置，
     * 要不然访问未验证的接口将重定向到登录页面，前后端分离的情况下这样并不友好，无权访问接口返回相关错误信息即可
     *
     * @param http
     * @return void
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin().loginPage("/login").and()
                .logout().addLogoutHandler(sysLogoutHandler).logoutSuccessHandler(sysLogoutSuccessHandler)
                .and()
                // 由于使用的是JWT，我们这里不需要csrf
                .csrf().disable().cors().and()
                .authorizeRequests()
                .antMatchers("/login", "/oauth/authorize").permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();

    }
}
