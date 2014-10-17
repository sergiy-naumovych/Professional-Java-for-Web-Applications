package com.wrox.site;

import com.wrox.config.annotation.WebController;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebController
@RequestMapping("session")
public class SessionListController
{
    @Inject SessionRegistry sessionRegistry;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Map<String, Object> model)
    {
        List<SessionInformation> sessions = new ArrayList<>();
        for(Object principal : this.sessionRegistry.getAllPrincipals())
            sessions.addAll(this.sessionRegistry.getAllSessions(principal, true));

        model.put("timestamp", System.currentTimeMillis());
        model.put("numberOfSessions", sessions.size());
        model.put("sessionList", sessions);

        return "session/list";
    }
}
