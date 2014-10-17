package com.wrox.site;

import com.wrox.site.validation.Email;
import com.wrox.site.validation.NotBlank;

import javax.validation.constraints.NotNull;

public class EmployeeForm
{
    @NotBlank(message = "{validate.employee.firstName}")
    private String firstName;

    @NotBlank(message = "{validate.employee.lastName}")
    private String lastName;

    private String middleName;

    @NotNull(message = "{validate.employee.email}")
    @Email(message = "{validate.employee.email}")
    private String email;

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

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
}
