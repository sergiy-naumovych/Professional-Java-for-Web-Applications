package com.wrox.site;

import com.wrox.site.entities.Publisher;
import org.springframework.stereotype.Repository;

@Repository
public class DefaultPublisherRepository
        extends GenericJpaRepository<Long, Publisher>
        implements PublisherRepository
{

}
