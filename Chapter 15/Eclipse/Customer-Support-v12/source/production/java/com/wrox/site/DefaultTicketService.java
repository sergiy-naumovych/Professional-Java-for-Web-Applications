package com.wrox.site;

import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.Instant;
import java.util.List;

@Service
public class DefaultTicketService implements TicketService
{
    @Inject TicketRepository ticketRepository;

    @Override
    public List<Ticket> getAllTickets()
    {
        return this.ticketRepository.getAll();
    }

    @Override
    public Ticket getTicket(long id)
    {
        return this.ticketRepository.get(id);
    }

    @Override
    public void save(Ticket ticket)
    {
        if(ticket.getId() < 1)
        {
            ticket.setDateCreated(Instant.now());
            this.ticketRepository.add(ticket);
        }
        else
            this.ticketRepository.update(ticket);
    }
}
