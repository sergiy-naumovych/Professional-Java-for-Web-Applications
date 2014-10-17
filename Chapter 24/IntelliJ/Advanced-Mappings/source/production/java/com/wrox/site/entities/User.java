package com.wrox.site.entities;

import com.wrox.site.converters.InstantConverter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "UserPrincipal")
public class User
{
    private long id;
    private Instant dateJoined;
    private String username;

    @Id
    @Column(name = "UserId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId()
    {
        return this.id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    @Convert(converter = InstantConverter.class)
    public Instant getDateJoined()
    {
        return this.dateJoined;
    }

    public void setDateJoined(Instant dateJoined)
    {
        this.dateJoined = dateJoined;
    }

    @Basic
    public String getUsername()
    {
        return this.username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }
}
