package com.hz.oauth2.server.service.impl;

import com.hz.oauth2.server.entity.authority.SysClientDetails;
import com.hz.oauth2.server.service.SysClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @program: authority-management-sys-2.0
 * @author: zgr
 * @create: 2021-08-03 10:12
 **/

@Service
@Slf4j
public class SysClientServiceImpl implements SysClientService {


    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        log.info("查找客户端id为 {} 的客户端详情", clientId);

        //实际使用中可以从数据库查询客户端详细信息，本例子中直接构造一个客户端

        Set<String> resourceIds = new HashSet<>();
        resourceIds.add("userservice1");

        Set<String> scopes = new HashSet<>(1);
        scopes.add("sever");

        Set<String> grantTypes = new HashSet<>(5);
        grantTypes.add("password");
        grantTypes.add("authorization_code");

        Set<String> registeredRedirectUris = new HashSet<>(1);
        registeredRedirectUris.add("https://baidu.com");

        return SysClientDetails.builder()
                .clientId("userservice1")
                .resourceId(resourceIds)
                .isSecretRequired(true)
                .clientSecret(new BCryptPasswordEncoder().encode("1234"))
                .scope(scopes)
                .authorizedGrantTypes(grantTypes)
                .registeredRedirectUri(registeredRedirectUris)
                .isScoped(true)
                .accessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(2))
                .refreshTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
                .isAutoApprove(true)
                .build();
    }
}
