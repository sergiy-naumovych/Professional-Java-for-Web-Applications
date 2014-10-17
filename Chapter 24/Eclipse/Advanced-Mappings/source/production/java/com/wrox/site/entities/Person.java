package com.wrox.site.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

@Entity
public class Person
{
    private long id;
    private String firstName;
    private String lastName;
    private PhoneNumber phoneNumber;
    private Address address;

    @Id
    @Column(name = "PersonId")
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

    @Embedded
    public PhoneNumber getPhoneNumber()
    {
        return this.phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "postalCode.code",
                    column = @Column(name = "Address_PostalCode_Code")),
            @AttributeOverride(name = "postalCode.suffix",
                    column = @Column(name = "Address_PostalCode_Suffix"))
    })
    public Address getAddress()
    {
        return this.address;
    }

    public void setAddress(Address address)
    {
        this.address = address;
    }

    private static final Logger log = LogManager.getLogger();

    @PostLoad void readTrigger()
    {
        log.debug("Person entity read.");
    }

    @PrePersist void beforeInsertTrigger()
    {
        log.debug("Person entity about to be inserted.");
    }

    @PostPersist void afterInsertTrigger()
    {
        log.debug("Person entity inserted into database.");
    }

    @PreUpdate void beforeUpdateTrigger()
    {
        log.debug("Person entity just updated by call to mutator method.");
    }

    @PostUpdate void afterUpdateTrigger()
    {
        log.debug("Person entity just updated in the database.");
    }

    @PreRemove void beforeDeleteTrigger()
    {
        log.debug("Person entity about to be deleted.");
    }

    @PostRemove void afterDeleteTrigger()
    {
        log.debug("Person entity about deleted from database.");
    }
}
