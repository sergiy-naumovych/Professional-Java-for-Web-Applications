package com.wrox.site;

public class Attachment
{
    private long id;
    private String name;
    private String mimeContentType;
    private byte[] contents;

    public long getId()
    {
        return this.id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getMimeContentType()
    {
        return this.mimeContentType;
    }

    public void setMimeContentType(String mimeContentType)
    {
        this.mimeContentType = mimeContentType;
    }

    public byte[] getContents()
    {
        return this.contents;
    }

    public void setContents(byte[] contents)
    {
        this.contents = contents;
    }
}
