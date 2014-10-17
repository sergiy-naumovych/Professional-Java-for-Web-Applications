package com.wrox.site.entities;

import com.wrox.site.converters.InstantConverter;

import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

@MappedSuperclass
public abstract class AuditedEntity extends VersionedEntity
{
    private Instant dateCreated;
    private Instant dateModified;

    @Convert(converter = InstantConverter.class)
    public Instant getDateCreated()
    {
        return this.dateCreated;
    }

    public void setDateCreated(Instant dateCreated)
    {
        this.dateCreated = dateCreated;
    }

    @Convert(converter = InstantConverter.class)
    public Instant getDateModified()
    {
        return this.dateModified;
    }

    public void setDateModified(Instant dateModified)
    {
        this.dateModified = dateModified;
    }
}
