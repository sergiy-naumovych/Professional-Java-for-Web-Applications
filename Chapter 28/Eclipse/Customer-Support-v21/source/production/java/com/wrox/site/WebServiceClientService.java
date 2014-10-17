package com.wrox.site;

import com.wrox.site.entities.WebServiceClient;
import org.springframework.security.oauth2.provider.ClientDetailsService;

public interface WebServiceClientService extends ClientDetailsService
{
    @Override
    WebServiceClient loadClientByClientId(String clientId);
}
