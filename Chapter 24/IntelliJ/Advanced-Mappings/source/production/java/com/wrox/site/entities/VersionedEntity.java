package com.wrox.site.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class VersionedEntity extends BaseEntity
{
    private long version;

    @Version
    @Column(name = "Revision")
    public long getVersion()
    {
        return this.version;
    }

    void setVersion(long version)
    {
        this.version = version;
    }
}
