package com.wrox.site.entities;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "OAuthAccessToken")
public class SigningAccessToken implements OAuth2AccessToken, Serializable
{
    public static final String SIGNING_TYPE = "Signing";
    public static final String SIGNING_TYPE_LOWER = SIGNING_TYPE.toLowerCase();

    private static final long serialVersionUID = 1L;

    private long id;
    private String key;
    private String value;
    private Date expiration;
    private Set<String> scope;
    private OAuth2Authentication authentication;

    public SigningAccessToken() { }

    public SigningAccessToken(String key, String value, Date expiration,
                              Set<String> scope,
                              OAuth2Authentication authentication)
    {
        this.key = key;
        this.value = value;
        this.expiration = expiration;
        this.scope = scope;
        this.authentication = authentication;
    }

    @Id
    @Column(name = "OAuthAccessTokenId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId()
    {
        return this.id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    @Column(name = "TokenKey")
    public String getKey()
    {
        return this.key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    @Override
    public String getValue()
    {
        return this.value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    @Override
    @Temporal(TemporalType.TIMESTAMP)
    public Date getExpiration()
    {
        return this.expiration;
    }

    public void setExpiration(Date expiration)
    {
        this.expiration = expiration;
    }

    @Override
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "OAuthAccessToken_Scope", joinColumns = {
            @JoinColumn(name = "OAuthAccessTokenId",
                    referencedColumnName = "OAuthAccessTokenId")
    })
    @Column(name = "Scope")
    public Set<String> getScope()
    {
        return this.scope;
    }

    public void setScope(Set<String> scope)
    {
        this.scope = scope;
    }

    @Lob
    public OAuth2Authentication getAuthentication()
    {
        return authentication;
    }

    public void setAuthentication(OAuth2Authentication authentication)
    {
        this.authentication = authentication;
    }

    @Override
    @Transient
    public final String getTokenType()
    {
        return SIGNING_TYPE_LOWER;
    }

    @Override
    @Transient
    public OAuth2RefreshToken getRefreshToken()
    {
        return null;
    }

    @Override
    @Transient
    public boolean isExpired()
    {
        return this.expiration.getTime() < System.currentTimeMillis();
    }

    @Override
    @Transient
    public int getExpiresIn()
    {
        return (int)(this.expiration.getTime() - System.currentTimeMillis()) /
                1_000;
    }

    // This makes Spring Security include the token_id in the token response
    @Override
    @Transient
    public Map<String, Object> getAdditionalInformation()
    {
        Map<String, Object> value = new HashMap<>();
        value.put("token_id", this.getKey());
        return value;
    }
}
