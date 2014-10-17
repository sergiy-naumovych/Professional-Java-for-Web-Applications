package com.wrox.site;

import com.wrox.site.entities.Attachment;
import com.wrox.site.entities.Ticket;
import com.wrox.site.entities.TicketComment;
import com.wrox.site.repositories.SearchResult;
import com.wrox.site.validation.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface TicketService
{
    @NotNull
    @PreAuthorize("hasAuthority('VIEW_TICKETS')")
    List<Ticket> getAllTickets();
    @NotNull
    @PreAuthorize("hasAuthority('VIEW_TICKETS')")
    Page<SearchResult<Ticket>> search(
            @NotBlank(message = "{validate.ticketService.search.query}")
                String query,
            boolean useBooleanMode,
            @NotNull(message = "{validate.ticketService.search.page}")
                Pageable pageable
    );
    @PreAuthorize("hasAuthority('VIEW_TICKET')")
    Ticket getTicket(
            @Min(value = 1L, message = "{validate.ticketService.getTicket.id}")
                long id
    );
    @PreAuthorize("#ticket.id == 0 and hasAuthority('CREATE_TICKET')")
    void create(@NotNull(message = "{validate.ticketService.save.ticket}")
                @Valid @P("ticket") Ticket ticket);
    @PreAuthorize("(authentication.principal.equals(#ticket.customer) and " +
            "hasAuthority('EDIT_OWN_TICKET')) or hasAuthority('EDIT_ANY_TICKET')")
    void update(@NotNull(message = "{validate.ticketService.save.ticket}")
                @Valid @P("ticket") Ticket ticket);
    @PreAuthorize("hasAuthority('DELETE_TICKET')")
    void deleteTicket(long id);

    @NotNull
    @PreAuthorize("hasAuthority('VIEW_COMMENTS')")
    Page<TicketComment> getComments(
            @Min(value = 1L, message = "{validate.ticketService.getComments.id}")
                long ticketId,
            @NotNull(message = "{validate.ticketService.getComments.page}")
                Pageable page
    );
    @PreAuthorize("#comment.id == 0 and hasAuthority('CREATE_COMMENT')")
    void create(
            @NotNull(message = "{validate.ticketService.save.comment}")
            @Valid @P("comment") TicketComment comment,
            @Min(value = 1L, message = "{validate.ticketService.saveComment.id}")
                long ticketId
    );
    @PreAuthorize("(authentication.principal.equals(#comment.customer) and " +
            "hasAuthority('EDIT_OWN_COMMENT')) or " +
            "hasAuthority('EDIT_ANY_COMMENT')")
    void update(@NotNull(message = "{validate.ticketService.save.comment}")
                @Valid @P("comment") TicketComment comment);
    @PreAuthorize("hasAuthority('DELETE_COMMENT')")
    void deleteComment(long id);

    @PreAuthorize("hasAuthority('VIEW_ATTACHMENT')")
    Attachment getAttachment(long id);
}
