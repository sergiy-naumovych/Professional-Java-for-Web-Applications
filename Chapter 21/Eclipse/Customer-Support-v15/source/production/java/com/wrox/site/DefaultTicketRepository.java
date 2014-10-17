package com.wrox.site;

import com.wrox.site.entities.TicketEntity;
import org.springframework.stereotype.Repository;

@Repository
public class DefaultTicketRepository
        extends GenericJpaRepository<Long, TicketEntity>
        implements TicketRepository
{
}
