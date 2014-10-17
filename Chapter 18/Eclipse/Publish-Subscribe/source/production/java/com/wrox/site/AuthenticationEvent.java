package com.wrox.site;

import org.springframework.context.ApplicationEvent;

public abstract class AuthenticationEvent extends ApplicationEvent
{
    public AuthenticationEvent(Object source)
    {
        super(source);
    }
}
