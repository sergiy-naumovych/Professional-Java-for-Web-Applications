package com.wrox.site;

import com.wrox.site.validation.Email;
import com.wrox.site.validation.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

public class Employee
{
    private long id;

    @NotBlank(message = "{validate.employee.firstName}")
    private String firstName;

    @NotBlank(message = "{validate.employee.lastName}")
    private String lastName;

    private String middleName;

    @NotBlank(message = "{validate.employee.governmentId}")
    private String governmentId;

    @NotNull(message = "{validate.employee.birthDate}")
    @Past(message = "{validate.employee.birthDate}")
    private Date birthDate;

    @NotNull(message = "{validate.employee.gender}")
    private Gender gender;

    @NotBlank(message = "{validate.employee.badgeNumber}")
    private String badgeNumber;

    @NotBlank(message = "{validate.employee.address}")
    private String address;

    @NotBlank(message = "{validate.employee.city}")
    private String city;

    @NotBlank(message = "{validate.employee.state}")
    private String state;

    @NotBlank(message = "{validate.employee.phoneNumber}")
    private String phoneNumber;

    @NotNull(message = "{validate.employee.email}")
    @Email(message = "{validate.employee.email}")
    private String email;

    @NotBlank(message = "{validate.employee.department}")
    private String department;

    @NotBlank(message = "{validate.employee.location}")
    private String location;

    @NotBlank(message = "{validate.employee.position}")
    private String position;

    public long getId()
    {
        return this.id;
    }

    public void setId(long id)
    {
        this.id = id;
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

    public String getMiddleName()
    {
        return middleName;
    }

    public void setMiddleName(String middleName)
    {
        this.middleName = middleName;
    }

    public String getGovernmentId()
    {
        return governmentId;
    }

    public void setGovernmentId(String governmentId)
    {
        this.governmentId = governmentId;
    }

    public Date getBirthDate()
    {
        return birthDate;
    }

    public void setBirthDate(Date birthDate)
    {
        this.birthDate = birthDate;
    }

    public Gender getGender()
    {
        return gender;
    }

    public void setGender(Gender gender)
    {
        this.gender = gender;
    }

    public String getBadgeNumber()
    {
        return badgeNumber;
    }

    public void setBadgeNumber(String badgeNumber)
    {
        this.badgeNumber = badgeNumber;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getDepartment()
    {
        return department;
    }

    public void setDepartment(String department)
    {
        this.department = department;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public String getPosition()
    {
        return position;
    }

    public void setPosition(String position)
    {
        this.position = position;
    }
}
