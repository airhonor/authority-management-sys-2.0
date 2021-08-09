package com.hz.oauth2.server.config;

import com.hz.oauth2.server.constant.Constant;
import com.hz.oauth2.server.service.SysClientService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * @program: OAuth-2.0
 * @author: zgr
 * @create: 2021-07-20 11:42
 **/
@Configuration
@EnableAuthorizationServer
public class SysAuthorizationConfig extends AuthorizationServerConfigurerAdapter {


    private final DataSource dataSource;

    private final SysClientService sysClientService;

    private final AuthenticationManager authenticationManager;

    public SysAuthorizationConfig(DataSource dataSource, SysClientService sysClientService, AuthenticationManager authenticationManager) {
        this.dataSource = dataSource;
        this.sysClientService = sysClientService;
        this.authenticationManager = authenticationManager;
    }


    /**
     * 维护一套客户端的信息 即第三方应用软件申请时记录的一些基本信息
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(sysClientService);
    }

    /**
     * 打开验证token的访问权限
     *
     * @param security
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
                .allowFormAuthenticationForClients()
                .tokenKeyAccess("isAuthenticated()")
                .checkTokenAccess("permitAll()")
                .passwordEncoder(new BCryptPasswordEncoder());
    }


    /**
     * 配置了令牌存储方式为jwt
     * 配置JWT Token的非对称加密来进行签名
     * 配置一个自定义的Token增强器，把更多信息放入Token中
     * 配置使用JDBC数据库方式来保存用户的授权批准记录
     *
     * @param endpoints
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), jwtTokenEnhancer()));

        endpoints.approvalStore(approvalStore())
                .authorizationCodeServices(authorizationCodeServices())
                .tokenEnhancer(tokenEnhancerChain)
                .tokenStore(tokenStore())
                .authenticationManager(authenticationManager);
    }

    /**
     * 使用数据库方式来保存授权码
     *
     * @return
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    /**
     * 采用jwt方式存储token
     *
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtTokenEnhancer());
    }

    /**
     * 使用数据库方式存储用户的批准授权记录
     *
     * @return
     */
    @Bean
    public JdbcApprovalStore approvalStore() {
        return new JdbcApprovalStore(dataSource);
    }

    /**
     * 自定义token增强器
     *
     * @return
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new SysTokenEnhancer();
    }

    /**
     * jwt 验证
     *
     * @return
     */
    @Bean
    protected JwtAccessTokenConverter jwtTokenEnhancer() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        // 设置jwt加解密秘钥，不设置会随机一个
        jwtAccessTokenConverter.setSigningKey(Constant.JWT_SECRET);
        return jwtAccessTokenConverter;
    }
}



