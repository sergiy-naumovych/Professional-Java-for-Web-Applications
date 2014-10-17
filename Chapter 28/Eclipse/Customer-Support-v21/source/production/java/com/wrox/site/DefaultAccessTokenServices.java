package com.wrox.site;

import com.wrox.site.entities.SigningAccessToken;
import com.wrox.site.repositories.SigningAccessTokenRepository;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Date;
import java.util.UUID;

public class DefaultAccessTokenServices implements SigningAccessTokenServices
{
    AuthenticationKeyGenerator authenticationKeyGenerator =
            new DefaultAuthenticationKeyGenerator();

    @Inject SigningAccessTokenRepository repository;

    @Override
    @Transactional
    public OAuth2AccessToken createAccessToken(OAuth2Authentication auth)
            throws AuthenticationException
    {
        String key = this.authenticationKeyGenerator.extractKey(auth);
        SigningAccessToken token = this.repository.getByKey(key);
        if(token != null)
        {
            if(token.isExpired())
            {
                this.repository.delete(token);
                this.repository.flush();
            }
            else
            {
                token.setAuthentication(auth); // in case authorities changed
                this.repository.save(token);
                return token;
            }
        }

        token = new SigningAccessToken(
                key,
                UUID.randomUUID().toString(),
                new Date(System.currentTimeMillis() + 86_400_000L), // one day
                auth.getAuthorizationRequest().getScope(),
                auth
        );

        this.repository.save(token);

        return token;
    }

    @Override
    @Transactional
    public OAuth2AccessToken getAccessToken(OAuth2Authentication auth)
    {
        return this.repository.getByKey(
                this.authenticationKeyGenerator.extractKey(auth)
        );
    }

    @Override
    @Transactional
    public SigningAccessToken getAccessToken(String key)
    {
        return this.repository.getByKey(key);
    }

    @Override
    @Transactional
    public OAuth2AccessToken readAccessToken(String tokenValue)
    {
        return this.repository.getByValue(tokenValue);
    }

    @Override
    @Transactional
    public OAuth2Authentication loadAuthentication(String tokenValue)
            throws AuthenticationException
    {
        SigningAccessToken token = this.repository.getByValue(tokenValue);
        if(token == null)
            throw new InvalidTokenException("Invalid token " + tokenValue + ".");

        if(token.isExpired())
        {
            this.repository.delete(token);
            throw new InvalidTokenException("Expired token " + tokenValue + ".");
        }

        return token.getAuthentication();
    }

    @Override
    public OAuth2AccessToken refreshAccessToken(String refreshToken,
                                                AuthorizationRequest request)
            throws AuthenticationException
    {
        throw new UnsupportedOperationException();
    }
}
