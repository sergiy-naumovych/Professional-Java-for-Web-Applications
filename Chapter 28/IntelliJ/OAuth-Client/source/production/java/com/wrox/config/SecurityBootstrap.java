package com.wrox.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

@SuppressWarnings("unused")
public class SecurityBootstrap extends AbstractSecurityWebApplicationInitializer
{
    @Override
    protected boolean enableHttpSessionEventPublisher()
    {
        return true;
    }
}
