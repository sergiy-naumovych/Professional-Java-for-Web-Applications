package com.wrox.site;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationInterestedParty
        implements ApplicationListener<AuthenticationEvent>
{
    private static final Logger log = LogManager.getLogger();

    @Override
    public void onApplicationEvent(AuthenticationEvent event)
    {
        log.info("Authentication event for IP address {}.", event.getSource());
    }
}
