package com.wrox.site;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.inject.Inject;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("ticket")
public class TicketController
{
    private static final Logger log = LogManager.getLogger();

    @Inject TicketService ticketService;

    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String list(Map<String, Object> model)
    {
        log.debug("Listing tickets.");
        model.put("tickets", this.ticketService.getAllTickets());

        return "ticket/list";
    }

    @RequestMapping(value = "view/{ticketId}", method = RequestMethod.GET)
    public ModelAndView view(Map<String, Object> model,
                             @PathVariable("ticketId") long ticketId)
    {
        Ticket ticket = this.ticketService.getTicket(ticketId);
        if(ticket == null)
            return this.getListRedirectModelAndView();
        model.put("ticketId", Long.toString(ticketId));
        model.put("ticket", ticket);
        return new ModelAndView("ticket/view");
    }

    @RequestMapping(
            value = "/{ticketId}/attachment/{attachment:.+}",
            method = RequestMethod.GET
    )
    public View download(@PathVariable("ticketId") long ticketId,
                         @PathVariable("attachment") String name)
    {
        Ticket ticket = this.ticketService.getTicket(ticketId);
        if(ticket == null)
            return this.getListRedirectView();

        Attachment attachment = ticket.getAttachment(name);
        if(attachment == null)
        {
            log.info("Requested attachment {} not found on ticket {}.", name, ticket);
            return this.getListRedirectView();
        }

        return new DownloadingView(attachment.getName(),
                attachment.getMimeContentType(), attachment.getContents());
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create(Map<String, Object> model)
    {
        model.put("ticketForm", new Form());
        return "ticket/add";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public View create(Principal principal, Form form) throws IOException
    {
        Ticket ticket = new Ticket();
        ticket.setCustomerName(principal.getName());
        ticket.setSubject(form.getSubject());
        ticket.setBody(form.getBody());

        for(MultipartFile filePart : form.getAttachments())
        {
            log.debug("Processing attachment for new ticket.");
            Attachment attachment = new Attachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            if((attachment.getName() != null && attachment.getName().length() > 0) ||
                    (attachment.getContents() != null && attachment.getContents().length > 0))
                ticket.addAttachment(attachment);
        }

        this.ticketService.save(ticket);

        return new RedirectView("/ticket/view/" + ticket.getId(), true, false);
    }

    private ModelAndView getListRedirectModelAndView()
    {
        return new ModelAndView(this.getListRedirectView());
    }

    private View getListRedirectView()
    {
        return new RedirectView("/ticket/list", true, false);
    }

    public static class Form
    {
        private String subject;
        private String body;
        private List<MultipartFile> attachments;

        public String getSubject()
        {
            return subject;
        }

        public void setSubject(String subject)
        {
            this.subject = subject;
        }

        public String getBody()
        {
            return body;
        }

        public void setBody(String body)
        {
            this.body = body;
        }

        public List<MultipartFile> getAttachments()
        {
            return attachments;
        }

        public void setAttachments(List<MultipartFile> attachments)
        {
            this.attachments = attachments;
        }
    }
}
