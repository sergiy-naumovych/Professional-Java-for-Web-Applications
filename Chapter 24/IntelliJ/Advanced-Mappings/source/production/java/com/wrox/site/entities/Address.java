package com.wrox.site.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class Address
{
    private String street;
    private String city;
    private String state;
    private String country;
    private PostalCode postalCode;

    @Basic
    @Column(name = "Address_Street")
    public String getStreet()
    {
        return this.street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    @Basic
    @Column(name = "Address_City")
    public String getCity()
    {
        return this.city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    @Basic
    @Column(name = "Address_State")
    public String getState()
    {
        return this.state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    @Basic
    @Column(name = "Address_Country")
    public String getCountry()
    {
        return this.country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    @Embedded
    public PostalCode getPostalCode()
    {
        return this.postalCode;
    }

    public void setPostalCode(PostalCode postalCode)
    {
        this.postalCode = postalCode;
    }
}
