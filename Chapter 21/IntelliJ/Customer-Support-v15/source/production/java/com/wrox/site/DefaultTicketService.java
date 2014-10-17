package com.wrox.site;

import com.wrox.site.entities.Attachment;
import com.wrox.site.entities.TicketEntity;
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
    @Inject AttachmentRepository attachmentRepository;
    @Inject UserRepository userRepository;

    @Override
    @Transactional
    public List<Ticket> getAllTickets()
    {
        List<Ticket> list = new ArrayList<>();
        this.ticketRepository.getAll().forEach(e -> list.add(this.convert(e)));
        return list;
    }

    @Override
    @Transactional
    public Ticket getTicket(long id)
    {
        TicketEntity entity = this.ticketRepository.get(id);
        return entity == null ? null : this.convert(entity);
    }

    private Ticket convert(TicketEntity entity)
    {
        Ticket ticket = new Ticket();
        ticket.setId(entity.getId());
        ticket.setCustomerName(
                this.userRepository.get(entity.getUserId()).getUsername()
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
            this.ticketRepository.add(entity);
            ticket.setId(entity.getId());
            for(Attachment attachment : ticket.getAttachments())
            {
                attachment.setTicketId(entity.getId());
                this.attachmentRepository.add(attachment);
            }
        }
        else
            this.ticketRepository.update(entity);
    }

    @Override
    @Transactional
    public void deleteTicket(long id)
    {
        this.ticketRepository.deleteById(id);
    }
}
