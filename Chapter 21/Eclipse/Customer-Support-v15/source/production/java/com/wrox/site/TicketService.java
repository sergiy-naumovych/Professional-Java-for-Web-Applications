package com.wrox.site;

import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface TicketService
{
    @NotNull
    List<Ticket> getAllTickets();
    Ticket getTicket(
            @Min(value = 1L, message = "{validate.ticketService.getTicket.id}")
                long id
    );
    void save(@NotNull(message = "{validate.ticketService.save.ticket}")
              @Valid Ticket ticket);
    void deleteTicket(long id);
}
