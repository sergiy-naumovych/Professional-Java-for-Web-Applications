package com.wrox.site;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InMemoryTicketRepository implements TicketRepository
{
    private volatile long TICKET_ID_SEQUENCE = 1L;

    private final Map<Long, Ticket> ticketDatabase = new LinkedHashMap<>();

    @Override
    public List<Ticket> getAll()
    {
        return new ArrayList<>(this.ticketDatabase.values());
    }

    @Override
    public Ticket get(long id)
    {
        return this.ticketDatabase.get(id);
    }

    @Override
    public void add(Ticket ticket)
    {
        ticket.setId(this.getNextTicketId());
        this.ticketDatabase.put(ticket.getId(), ticket);
    }

    @Override
    public void update(Ticket ticket)
    {
        this.ticketDatabase.put(ticket.getId(), ticket);
    }

    @Override
    public void delete(long id)
    {
        this.ticketDatabase.remove(id);
    }

    private synchronized long getNextTicketId()
    {
        return this.TICKET_ID_SEQUENCE++;
    }
}
