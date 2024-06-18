package com.dragon.flow.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @title:
 * @author: bruce.liu
 * @since: 2023/5/16 17:52
 */
@Data
@Configuration
public class SsoConfigProperties {

    @Value("${dragon.flow.flow.sso_url:http://172.24.100.107:14000/auth/oauth/check_token}")
    private String ssoUrl;
    @Value("${dragon.flow.flow.sso_appId:0}")
    private String ssoAppId;
    @Value("${dragon.flow.flow.sso_authorization:Basic cGlnOnBpZw==}")
    private String ssoAuthorization;
}
