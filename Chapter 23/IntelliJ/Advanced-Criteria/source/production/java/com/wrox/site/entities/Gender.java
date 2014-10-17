package com.wrox.site.entities;

public enum Gender
{
    MALE("Male"),
    FEMALE("Female"),
    UNSPECIFIED("Unspecified");

    private final String displayName;

    Gender(String displayName)
    {
        this.displayName = displayName;
    }

    public String getDisplayName()
    {
        return this.displayName;
    }
}
