package com.wrox.site;

import com.wrox.site.validation.NotBlank;

import javax.validation.constraints.Size;

public class Attachment
{
    @NotBlank(message = "{validate.attachment.name}")
    private String name;

    @NotBlank(message = "{validate.attachment.mimeContentType}")
    private String mimeContentType;

    @Size(min = 1, message = "{validate.attachment.contents}")
    private byte[] contents;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getMimeContentType()
    {
        return mimeContentType;
    }

    public void setMimeContentType(String mimeContentType)
    {
        this.mimeContentType = mimeContentType;
    }
    public byte[] getContents()
    {
        return contents;
    }

    public void setContents(byte[] contents)
    {
        this.contents = contents;
    }

}
