package com.wrox.site.entities;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Applicant
{
    private long id;
    private String firstName;
    private String lastName;
    private boolean citizen;
    private Set<Resume> résumés = new HashSet<>();

    @Id
    @Column(name = "ApplicantId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId()
    {
        return this.id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    @Basic
    public String getFirstName()
    {
        return this.firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    @Basic
    public String getLastName()
    {
        return this.lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    @Basic
    public boolean isCitizen()
    {
        return this.citizen;
    }

    public void setCitizen(boolean citizen)
    {
        this.citizen = citizen;
    }

    @OneToMany(mappedBy = "applicant", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<Resume> getRésumés()
    {
        return this.résumés;
    }

    public void setRésumés(Set<Resume> résumés)
    {
        this.résumés = résumés;
    }
}
