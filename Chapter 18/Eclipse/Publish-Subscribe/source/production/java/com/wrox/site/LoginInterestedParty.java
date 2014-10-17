package com.wrox.site;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
public class LoginInterestedParty implements ApplicationListener<LoginEvent>
{
    private static final Logger log = LogManager.getLogger();

    @Override
    public void onApplicationEvent(LoginEvent event)
    {
        log.info("Login event for IP address {}.", event.getSource());
    }
}
