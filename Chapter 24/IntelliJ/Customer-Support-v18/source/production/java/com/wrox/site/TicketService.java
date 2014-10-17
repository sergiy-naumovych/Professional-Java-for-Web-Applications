package com.wrox.site;

import com.wrox.site.entities.Attachment;
import com.wrox.site.entities.Ticket;
import com.wrox.site.entities.TicketComment;
import com.wrox.site.repositories.SearchResult;
import com.wrox.site.validation.NotBlank;
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
    @NotNull
    Page<SearchResult<Ticket>> search(
            @NotBlank(message = "{validate.ticketService.search.query}")
                String query,
            boolean useBooleanMode,
            @NotNull(message = "{validate.ticketService.search.page}")
                Pageable pageable
    );
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

    Attachment getAttachment(long id);
}
