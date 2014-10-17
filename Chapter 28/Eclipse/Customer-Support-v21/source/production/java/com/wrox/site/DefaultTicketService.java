package com.wrox.site;

import com.wrox.site.entities.Attachment;
import com.wrox.site.entities.Ticket;
import com.wrox.site.entities.TicketComment;
import com.wrox.site.repositories.AttachmentRepository;
import com.wrox.site.repositories.SearchResult;
import com.wrox.site.repositories.TicketCommentRepository;
import com.wrox.site.repositories.TicketRepository;
import com.wrox.site.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultTicketService implements TicketService
{
    @Inject TicketRepository ticketRepository;
    @Inject TicketCommentRepository commentRepository;
    @Inject AttachmentRepository attachmentRepository;
    @Inject UserRepository userRepository;

    @Override
    @Transactional
    public List<Ticket> getAllTickets(TicketExpand... expands)
    {
        Iterable<Ticket> iterable = this.ticketRepository.findAll();
        List<Ticket> list = new ArrayList<>();
        iterable.forEach(t -> {
            for(TicketExpand expand : expands)
                expand.accept(t);
            list.add(t);
        });
        return list;
    }

    @Override
    @Transactional
    public Page<SearchResult<Ticket>> search(String query, boolean useBooleanMode,
                                             Pageable pageable,
                                             TicketExpand... expands)
    {
        Page<SearchResult<Ticket>> results = this.ticketRepository
                .search(query, useBooleanMode, pageable);
        for(TicketExpand expand : expands)
            results.getContent().forEach(r -> expand.accept(r.getEntity()));
        return results;
    }

    @Override
    @Transactional
    public Ticket getTicket(long id, TicketExpand... expands)
    {
        Ticket ticket = this.ticketRepository.findOne(id);
        ticket.getNumberOfAttachments();
        for(TicketExpand expand : expands)
            expand.accept(ticket);
        return ticket;
    }

    @Override
    @Transactional
    public void create(Ticket ticket)
    {
        ticket.setDateCreated(Instant.now());
        this.ticketRepository.save(ticket);
    }

    @Override
    @Transactional
    public void update(Ticket ticket)
    {
        this.ticketRepository.save(ticket);
    }

    @Override
    @Transactional
    public void deleteTicket(long id)
    {
        this.ticketRepository.delete(id);
    }

    @Override
    @Transactional
    public Page<TicketComment> getComments(long ticketId, Pageable pageable,
                                           CommentExpand... expands)
    {
        Page<TicketComment> results = this.commentRepository
                .getByTicketId(ticketId, pageable);
        for(CommentExpand expand : expands)
            results.getContent().forEach(expand);
        return results;
    }

    @Override
    @Transactional
    public void create(TicketComment comment, long ticketId)
    {
        comment.setTicketId(ticketId);
        comment.setDateCreated(Instant.now());
        this.commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void update(TicketComment comment)
    {
        this.commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void deleteComment(long id)
    {
        this.commentRepository.delete(id);
    }

    @Override
    @Transactional
    public Attachment getAttachment(long id)
    {
        Attachment attachment = this.attachmentRepository.findOne(id);
        if(attachment != null)
            attachment.getContents();
        return attachment;
    }
}
