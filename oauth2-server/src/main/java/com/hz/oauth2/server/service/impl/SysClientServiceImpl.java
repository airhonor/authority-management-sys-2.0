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
        grantTypes.add("refresh_token");


        Set<String> registeredRedirectUris = new HashSet<>(1);
        registeredRedirectUris.add("https://baidu.com");

        return SysClientDetails.builder()
                //客户端id
                .clientId("userservice1")
                //拥有的资源id
                .resourceId(resourceIds)
                //是否需要secret
                .isSecretRequired(true)
                //secret加密方式
                .clientSecret(new BCryptPasswordEncoder().encode("1234"))
                //此客户端的范围。 如果客户端不在范围内，则为空
                .scope(scopes)
                //支持的授权模式，四种模式支持哪几种
                .authorizedGrantTypes(grantTypes)
                //官方注册的回调地址，这个地址是需要和授权请求的回调地址一致
                .registeredRedirectUri(registeredRedirectUris)
                .isScoped(true)
                //access_token的有效时间
                .accessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(2))
                //refresh_token的有效时间
                .refreshTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
                //是否可以自主授权
                .isAutoApprove(true)
                .build();
    }
}
