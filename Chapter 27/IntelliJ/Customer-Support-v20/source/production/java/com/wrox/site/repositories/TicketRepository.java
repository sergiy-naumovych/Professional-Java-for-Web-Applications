package com.wrox.site.repositories;

import com.wrox.site.entities.Ticket;
import org.springframework.data.repository.CrudRepository;

public interface TicketRepository extends CrudRepository<Ticket, Long>,
        SearchableRepository<Ticket>
{

}
