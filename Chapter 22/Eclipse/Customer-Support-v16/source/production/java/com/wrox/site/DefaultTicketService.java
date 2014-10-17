package com.wrox.site;

import com.wrox.site.entities.Attachment;
import com.wrox.site.entities.TicketCommentEntity;
import com.wrox.site.entities.TicketEntity;
import com.wrox.site.repositories.AttachmentRepository;
import com.wrox.site.repositories.TicketCommentRepository;
import com.wrox.site.repositories.TicketRepository;
import com.wrox.site.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.sql.Timestamp;
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
    public List<Ticket> getAllTickets()
    {
        List<Ticket> list = new ArrayList<>();
        this.ticketRepository.findAll().forEach(e -> list.add(this.convert(e)));
        return list;
    }

    @Override
    @Transactional
    public Ticket getTicket(long id)
    {
        TicketEntity entity = this.ticketRepository.findOne(id);
        return entity == null ? null : this.convert(entity);
    }

    private Ticket convert(TicketEntity entity)
    {
        Ticket ticket = new Ticket();
        ticket.setId(entity.getId());
        ticket.setCustomerName(
                this.userRepository.findOne(entity.getUserId()).getUsername()
        );
        ticket.setSubject(entity.getSubject());
        ticket.setBody(entity.getBody());
        ticket.setDateCreated(Instant.ofEpochMilli(
                entity.getDateCreated().getTime()
        ));
        this.attachmentRepository.getByTicketId(entity.getId())
                .forEach(ticket::addAttachment);
        return ticket;
    }

    @Override
    @Transactional
    public void save(Ticket ticket)
    {
        TicketEntity entity = new TicketEntity();
        entity.setId(ticket.getId());
        entity.setUserId(this.userRepository.getByUsername(
                ticket.getCustomerName()
        ).getId());
        entity.setSubject(ticket.getSubject());
        entity.setBody(ticket.getBody());

        if(ticket.getId() < 1)
        {
            ticket.setDateCreated(Instant.now());
            entity.setDateCreated(new Timestamp(
                    ticket.getDateCreated().toEpochMilli()
            ));
            this.ticketRepository.save(entity);
            ticket.setId(entity.getId());
            for(Attachment attachment : ticket.getAttachments())
            {
                attachment.setTicketId(entity.getId());
                this.attachmentRepository.save(attachment);
            }
        }
        else
            this.ticketRepository.save(entity);
    }

    @Override
    @Transactional
    public void deleteTicket(long id)
    {
        this.ticketRepository.delete(id);
    }

    @Override
    @Transactional
    public Page<TicketComment> getComments(long ticketId, Pageable page)
    {
        List<TicketComment> comments = new ArrayList<>();
        Page<TicketCommentEntity> entities =
                this.commentRepository.getByTicketId(ticketId, page);
        entities.forEach(e -> comments.add(this.convert(e)));

        return new PageImpl<>(comments, page, entities.getTotalElements());
    }

    private TicketComment convert(TicketCommentEntity entity)
    {
        TicketComment comment = new TicketComment();
        comment.setId(entity.getId());
        comment.setCustomerName(
                this.userRepository.findOne(entity.getUserId()).getUsername()
        );
        comment.setBody(entity.getBody());
        comment.setDateCreated(Instant.ofEpochMilli(
                entity.getDateCreated().getTime()
        ));

        return comment;
    }

    @Override
    @Transactional
    public void save(TicketComment comment, long ticketId)
    {
        TicketCommentEntity entity = new TicketCommentEntity();
        entity.setId(comment.getId());
        entity.setTicketId(ticketId);
        entity.setUserId(this.userRepository.getByUsername(
                comment.getCustomerName()
        ).getId());
        entity.setBody(comment.getBody());

        if(comment.getId() < 1)
        {
            comment.setDateCreated(Instant.now());
            entity.setDateCreated(new Timestamp(
                    comment.getDateCreated().toEpochMilli()
            ));
            this.commentRepository.save(entity);
            comment.setId(entity.getId());
        }
        else
            this.commentRepository.save(entity);
    }

    @Override
    @Transactional
    public void deleteComment(long id)
    {
        this.commentRepository.delete(id);
    }
}
