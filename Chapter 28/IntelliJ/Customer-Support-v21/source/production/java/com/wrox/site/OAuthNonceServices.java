package com.wrox.site;

public interface OAuthNonceServices
{
    void recordNonceOrFailIfDuplicate(String nonce, long timestamp);
}
