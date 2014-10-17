package com.wrox.site;

import java.util.List;

public interface TicketService
{
    List<Ticket> getAllTickets();
    Ticket getTicket(long id);
    void save(Ticket ticket);
}
