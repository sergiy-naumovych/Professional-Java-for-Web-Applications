package com.wrox.site.repositories;

import com.wrox.site.entities.SigningAccessToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SigningAccessTokenRepository
        extends JpaRepository<SigningAccessToken, Long>
{
    SigningAccessToken getByKey(String key);
    SigningAccessToken getByValue(String value);
}
