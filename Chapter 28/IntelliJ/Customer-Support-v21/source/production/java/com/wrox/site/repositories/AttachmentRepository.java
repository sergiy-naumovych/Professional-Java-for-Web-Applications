package com.wrox.site.repositories;

import com.wrox.site.entities.Attachment;
import org.springframework.data.repository.CrudRepository;

public interface AttachmentRepository extends CrudRepository<Attachment, Long>
{

}
