package com.wrox.site;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.ServletContext;

@Service
public class LogoutInterestedParty implements ApplicationListener<LogoutEvent>
{
    private static final Logger log = LogManager.getLogger();

    @Inject ServletContext servletContext;

    @Override
    @Async
    public void onApplicationEvent(LogoutEvent event)
    {
        log.info("Logout event for context {} received in context {}.",
                event.getSource(), this.servletContext.getContextPath());

        try
        {
            Thread.sleep(5000L);
        }
        catch(InterruptedException e)
        {
            log.error(e);
        }
    }
}
