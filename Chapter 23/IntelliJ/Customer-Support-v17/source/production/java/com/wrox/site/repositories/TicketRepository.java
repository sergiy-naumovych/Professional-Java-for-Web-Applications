package com.wrox.site.repositories;

import com.wrox.site.entities.TicketEntity;
import org.springframework.data.repository.CrudRepository;

public interface TicketRepository extends CrudRepository<TicketEntity, Long>,
        SearchableRepository<TicketEntity>
{

}
