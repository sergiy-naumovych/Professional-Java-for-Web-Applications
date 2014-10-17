package com.wrox.site;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

public class OAuth2SigningRestTemplate extends OAuth2RestTemplate
{
    private static final Logger log = LogManager.getLogger();
    private static final MessageDigest DIGEST;
    static
    {
        try
        {
            DIGEST = MessageDigest.getInstance("SHA-1");
        }
        catch(NoSuchAlgorithmException e) // not possible
        {
            throw new IllegalStateException(e);
        }
    }

    private final OAuth2ProtectedResourceDetails resource;

    public OAuth2SigningRestTemplate(OAuth2ProtectedResourceDetails resource)
    {
        super(resource);
        this.resource = resource;
    }

    public OAuth2SigningRestTemplate(OAuth2ProtectedResourceDetails resource,
                                     OAuth2ClientContext context)
    {
        super(resource, context);
        this.resource = resource;
    }

    @Override
    protected ClientHttpRequest createRequest(URI uri, HttpMethod method)
            throws IOException
    {
        OAuth2AccessToken token = this.getAccessToken();

        String tokenType = token.getTokenType();
        if(!StringUtils.hasText(tokenType))
            tokenType = OAuth2AccessToken.BEARER_TYPE;

        if("Signing".equalsIgnoreCase(tokenType))
        {
            String clientId = this.resource.getClientId();
            String tokenId = token.getAdditionalInformation()
                    .get("token_id").toString();
            String nonce = UUID.randomUUID().toString();
            long timestamp = System.currentTimeMillis() / 1_000L;

            String toSign = clientId + "," + tokenId + "," + nonce + "," +
                    timestamp + "," + method + "," + token.getValue();
            String signature = new String(Base64.getEncoder().encode(
                    DIGEST.digest(toSign.getBytes(StandardCharsets.UTF_8))
            ), StandardCharsets.UTF_8);

            String header = "Signing client_id=" + clientId + ", token_id=" +
                    tokenId + ", timestamp=" + timestamp + ", nonce=" + nonce +
                    ", signature=" + signature;

            ClientHttpRequest request =
                    this.getRequestFactory().createRequest(uri, method);
            log.debug("Created [{}] request for [{}].", method, uri);
            log.debug("toSign = [{}], signature = [{}]", toSign, signature);
            request.getHeaders().add("Authorization", header);
            return request;
        }
        else
            throw new OAuth2AccessDeniedException(
                    "Unsupported access token type [" + tokenType + "].");
    }
}
