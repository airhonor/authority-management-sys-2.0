package com.hz.oauth2.resource.ctrl;

import com.hz.oauth2.resource.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author honorzhang
 */

@RestController
@RequestMapping("/user")
public class ResourceUserController {

    @Autowired
    private TokenStore tokenStore;

    /***
     * 读权限或写权限可访问，返回登录用户名
     * @param authentication
     * @return
     */
    @PreAuthorize("hasAuthority('READ') or hasAuthority('WRITE')")
    @GetMapping("/name")
    public BaseResponse<String> name(OAuth2Authentication authentication) {
        return BaseResponse.success(authentication.getName());
    }

    /**
     * 读权限或写权限可访问，返回登录用户信息
     *
     * @param authentication
     * @return
     */
    @PreAuthorize("hasAuthority('READ') or hasAuthority('WRITE')")
    @GetMapping
    public BaseResponse<OAuth2Authentication> read(OAuth2Authentication authentication) {
        return BaseResponse.success(authentication);
    }

    /**
     * 只有写权限可以访问，返回访问令牌中的额外信息
     *
     * @param authentication
     * @return
     */
    @PreAuthorize("hasAuthority('WRITE')")
    @PostMapping
    public BaseResponse<Object> write(OAuth2Authentication authentication) {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(details.getTokenValue());
        return BaseResponse.success(accessToken.getAdditionalInformation().getOrDefault("userDetails", null));
    }
}
