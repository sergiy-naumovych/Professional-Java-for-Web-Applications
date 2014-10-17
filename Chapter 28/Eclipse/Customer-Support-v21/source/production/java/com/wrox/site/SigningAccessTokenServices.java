package com.wrox.site;

import com.wrox.site.entities.SigningAccessToken;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

public interface SigningAccessTokenServices
        extends AuthorizationServerTokenServices, ResourceServerTokenServices
{
    SigningAccessToken getAccessToken(String key);
}
