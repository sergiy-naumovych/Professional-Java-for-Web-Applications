package com.wrox.site;

import com.wrox.site.entities.Attachment;

public interface AttachmentRepository extends GenericRepository<Long, Attachment>
{
    Iterable<Attachment> getByTicketId(long ticketId);
}
