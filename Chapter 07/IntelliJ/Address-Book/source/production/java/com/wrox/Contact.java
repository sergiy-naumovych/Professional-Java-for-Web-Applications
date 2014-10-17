package com.wrox;

import java.time.Instant;
import java.time.MonthDay;

public class Contact implements Comparable<Contact>
{
    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String address;

    private MonthDay birthday;

    private Instant dateCreated;

    public Contact()
    {

    }

    public Contact(String firstName, String lastName, String phoneNumber,
                   String address, MonthDay birthday,
                   Instant dateCreated)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.birthday = birthday;
        this.dateCreated = dateCreated;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public MonthDay getBirthday()
    {
        return birthday;
    }

    public void setBirthday(MonthDay birthday)
    {
        this.birthday = birthday;
    }

    public Instant getDateCreated()
    {
        return dateCreated;
    }

    public void setDateCreated(Instant dateCreated)
    {
        this.dateCreated = dateCreated;
    }

    @Override
    public int compareTo(Contact other)
    {
        int last = lastName.compareTo(other.lastName);
        if(last == 0)
            return firstName.compareTo(other.firstName);
        return last;
    }
}
