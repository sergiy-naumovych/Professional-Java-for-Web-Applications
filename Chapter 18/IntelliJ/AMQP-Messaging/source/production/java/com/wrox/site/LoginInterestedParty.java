package com.wrox.site;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.ServletContext;

@Service
public class LoginInterestedParty implements ApplicationListener<LoginEvent>
{
    private static final Logger log = LogManager.getLogger();

    @Inject ServletContext servletContext;

    @Override
    public void onApplicationEvent(LoginEvent event)
    {
        log.info("Login event for context {} received in context {}.",
                event.getSource(), this.servletContext.getContextPath());
    }
}
