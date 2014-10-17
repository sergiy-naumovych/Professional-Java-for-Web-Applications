package com.wrox.site.entities;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.OrderColumn;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
public class Employee
{
    private long id;
    private String firstName;
    private String lastName;
    private List<String> phoneNumbers = new ArrayList<>();
    private Set<Address> addresses = new HashSet<>();
    private Map<String, String> extraProperties = new HashMap<>();

    @Id
    @Column(name = "EmployeeId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId()
    {
        return id;
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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "Employee_Phone", joinColumns = {
            @JoinColumn(name = "Employee", referencedColumnName = "EmployeeId")
    })
    @Column(name = "Number")
    @OrderColumn(name = "Priority")
    public List<String> getPhoneNumbers()
    {
        return this.phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers)
    {
        this.phoneNumbers = phoneNumbers;
    }

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "Employee_Address", joinColumns = {
            @JoinColumn(name = "Employee", referencedColumnName = "EmployeeId")
    })
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "Street")),
            @AttributeOverride(name = "city", column = @Column(name = "City")),
            @AttributeOverride(name = "state", column = @Column(name = "State")),
            @AttributeOverride(name = "country", column= @Column(name = "Country"))
    })
    public Set<Address> getAddresses()
    {
        return this.addresses;
    }

    public void setAddresses(Set<Address> addresses)
    {
        this.addresses = addresses;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "Employee_Property", joinColumns = {
            @JoinColumn(name = "Employee", referencedColumnName = "EmployeeId")
    })
    @Column(name = "Value")
    @MapKeyColumn(name = "KeyName")
    public Map<String, String> getExtraProperties()
    {
        return this.extraProperties;
    }

    public void setExtraProperties(Map<String, String> extraProperties)
    {
        this.extraProperties = extraProperties;
    }
}
