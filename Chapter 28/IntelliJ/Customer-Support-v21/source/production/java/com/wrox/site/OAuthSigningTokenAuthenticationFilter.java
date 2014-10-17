package com.wrox.site;

import com.wrox.site.entities.SigningAccessToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetailsSource;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;

public class OAuthSigningTokenAuthenticationFilter implements Filter
{
    private static final Logger log = LogManager.getLogger();
    private static final MessageDigest DIGEST;
    static {
        try {
            DIGEST = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) { // not possible
            throw new IllegalStateException(e);
        }
    }

    private SigningAccessTokenServices tokenServices;
    private OAuthNonceServices nonceServices;
    private AuthenticationEntryPoint authenticationEntryPoint =
            new OAuth2AuthenticationEntryPoint();
    private AuthenticationDetailsSource<HttpServletRequest, ?>
            authenticationDetailsSource = new OAuth2AuthenticationDetailsSource();
    private String resourceId;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException
    {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponse httpResponse = (HttpServletResponse)response;

        try
        {
            Map<String, String> header = this.parseHeader(httpRequest);
            if(header == null)
                log.debug("No token in request, will continue chain.");
            else
                this.authenticate(header, httpRequest);
        }
        catch(OAuth2Exception e)
        {
            log.info("Authentication request failed.", e);

            SecurityContextHolder.clearContext();
            this.authenticationEntryPoint.commence(
                    httpRequest, httpResponse,
                    new InsufficientAuthenticationException(e.getMessage(), e)
            );
            return;
        }

        chain.doFilter(request, response);
    }

    private Map<String, String> parseHeader(HttpServletRequest request)
    {
        Enumeration<String> headers = request.getHeaders("Authorization");
        while(headers.hasMoreElements())
        {
            String value = headers.nextElement();
            if(value.toLowerCase()
                    .startsWith(SigningAccessToken.SIGNING_TYPE_LOWER))
            {
                String pairsValue = value.substring(
                        SigningAccessToken.SIGNING_TYPE.length()
                ).trim();
                if(pairsValue.length() == 0)
                    throw new InvalidTokenException("Missing pairs.");
                String[] pairs = pairsValue.split(",");
                if(pairs.length != 5)
                    throw new InvalidTokenException(
                            "Illegal number of pairs in [" + pairsValue + "].");
                Map<String, String> header = new Hashtable<>();
                for(String pair : pairs)
                {
                    String[] keyValue = pair.split("=", 2);
                    if(keyValue.length != 2)
                        throw new InvalidTokenException(
                                "At least one pair is not key/value in [" +
                                        pairsValue + "].");
                    header.put(keyValue[0].trim(), keyValue[1].trim());
                }
                return header;
            }
        }

        return null;
    }

    private void authenticate(Map<String, String> header,
                              HttpServletRequest request)
    {
        String tokenId = header.get("token_id");
        if(tokenId == null)
            throw new InvalidTokenException("Header [" + header +
                    "] missing token_id.");

        SigningAccessToken token = this.tokenServices.getAccessToken(tokenId);
        if(token == null)
            throw new InvalidTokenException("Token [" + tokenId + "] not found.");

        OAuth2Authentication authentication = token.getAuthentication();
        AuthorizationRequest authorizationRequest =
                authentication.getAuthorizationRequest();

        String clientId = header.get("client_id");
        if(!authorizationRequest.getClientId().equals(clientId))
            throw new InvalidTokenException("Client ID does not match token.");

        Collection<String> resourceIds = authorizationRequest.getResourceIds();
        if(this.resourceId != null && resourceIds != null &&
                !resourceIds.isEmpty() && !resourceIds.contains(this.resourceId))
            throw new InvalidTokenException("Resource ID not permitted.");

        String timestamp = header.get("timestamp");
        String nonce = header.get("nonce");
        if(timestamp == null || nonce == null)
            throw new InvalidTokenException("Header missing timestamp or nonce.");

        String toSign = clientId + "," + tokenId + "," + nonce + "," +
                timestamp + "," + request.getMethod().toUpperCase() + "," +
                token.getValue();
        String signature = new String(Base64.getEncoder().encode(
                DIGEST.digest(toSign.getBytes(StandardCharsets.UTF_8))
        ), StandardCharsets.UTF_8);
        String presentedSignature = header.get("signature");
        if(!signature.equals(presentedSignature))
            throw new InvalidTokenException("Missing or invalid signature.");

        long timestampValue = Long.parseLong(timestamp);
        long now = System.currentTimeMillis() / 1_000L;
        if(timestampValue < now - 60L || timestampValue > now + 60L)
            throw new InvalidTokenException("Header timestamp out of range.");

        this.nonceServices.recordNonceOrFailIfDuplicate(nonce, timestampValue);

        request.setAttribute(OAuth2AuthenticationDetails.ACCESS_TOKEN_VALUE,
                token.getValue());
        authentication.setDetails(this.authenticationDetailsSource.buildDetails(
                request
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public void setTokenServices(SigningAccessTokenServices tokenServices)
    {
        this.tokenServices = tokenServices;
    }

    public void setNonceServices(OAuthNonceServices nonceServices)
    {
        this.nonceServices = nonceServices;
    }

    public void setAuthenticationEntryPoint(AuthenticationEntryPoint point)
    {
        this.authenticationEntryPoint = point;
    }

    public void setAuthenticationDetailsSource(
            AuthenticationDetailsSource<HttpServletRequest, ?>
                    authenticationDetailsSource)
    {
        this.authenticationDetailsSource = authenticationDetailsSource;
    }

    public void setResourceId(String resourceId)
    {
        this.resourceId = resourceId;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void destroy() { }
}
