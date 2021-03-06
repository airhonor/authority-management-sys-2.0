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
     * ?????????????????????????????? ????????????????????????????????????????????????????????????
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(sysClientService);
    }

    /**
     * ????????????token???????????????
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
     * ??????????????????????????????jwt
     * ??????JWT Token?????????????????????????????????
     * ????????????????????????Token?????????????????????????????????Token???
     * ????????????JDBC???????????????????????????????????????????????????
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
     * ???????????????????????????????????????
     *
     * @return
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    /**
     * ??????jwt????????????token
     *
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtTokenEnhancer());
    }

    /**
     * ??????????????????????????????????????????????????????
     *
     * @return
     */
    @Bean
    public JdbcApprovalStore approvalStore() {
        return new JdbcApprovalStore(dataSource);
    }

    /**
     * ?????????token?????????
     *
     * @return
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new SysTokenEnhancer();
    }

    /**
     * jwt ??????
     *
     * @return
     */
    @Bean
    protected JwtAccessTokenConverter jwtTokenEnhancer() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        // ??????jwt??????????????????????????????????????????
        jwtAccessTokenConverter.setSigningKey(Constant.JWT_SECRET);
        return jwtAccessTokenConverter;
    }
}



