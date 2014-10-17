package com.wrox.site.repositories;

import com.wrox.site.entities.WebServiceClient;
import org.springframework.data.repository.CrudRepository;

public interface WebServiceClientRepository
        extends CrudRepository<WebServiceClient, Long>
{
    WebServiceClient getByClientId(String clientId);
}
