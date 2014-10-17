package com.wrox.site.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PostalCode
{
    private String code;
    private String suffix;

    @Basic
    @Column(name = "PostalCode_Code")
    public String getCode()
    {
        return this.code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    @Basic
    @Column(name = "PostalCode_Suffix")
    public String getSuffix()
    {
        return this.suffix;
    }

    public void setSuffix(String suffix)
    {
        this.suffix = suffix;
    }
}
