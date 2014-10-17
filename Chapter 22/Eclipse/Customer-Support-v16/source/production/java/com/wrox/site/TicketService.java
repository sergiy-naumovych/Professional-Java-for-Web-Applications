package com.wrox.site;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @NotNull
    Page<TicketComment> getComments(
            @Min(value = 1L, message = "{validate.ticketService.getComments.id}")
                long ticketId,
            @NotNull(message = "{validate.ticketService.getComments.page}")
                Pageable page
    );
    void save(
            @NotNull(message = "{validate.ticketService.save.comment}")
            @Valid TicketComment comment,
            @Min(value = 1L, message = "{validate.ticketService.saveComment.id}")
                long ticketId
    );
    void deleteComment(long id);
}
