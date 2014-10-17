package com.wrox.site;

import com.wrox.site.entities.WebServiceClient;
import com.wrox.site.repositories.WebServiceClientRepository;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

public class DefaultWebServiceClientService implements WebServiceClientService
{
    @Inject WebServiceClientRepository clientRepository;

    @Override
    @Transactional
    public WebServiceClient loadClientByClientId(String clientId)
    {
        WebServiceClient client = this.clientRepository.getByClientId(clientId);
        if(client == null)
            throw new ClientRegistrationException("Client not found");
        return client;
    }
}
