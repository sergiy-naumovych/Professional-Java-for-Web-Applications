package com.wrox.site;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Map;

@Controller
public class AuthenticationController
{
    private static final Logger log = LogManager.getLogger();

    @Inject AuthenticationService authenticationService;

    @RequestMapping("logout")
    public View logout(HttpServletRequest request, HttpSession session)
    {
        if(log.isDebugEnabled() && request.getUserPrincipal() != null)
            log.debug("User {} logged out.", request.getUserPrincipal().getName());
        session.invalidate();

        return new RedirectView("/login", true, false);
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public ModelAndView login(Map<String, Object> model, HttpSession session)
    {
        if(UserPrincipal.getPrincipal(session) != null)
            return this.getTicketRedirect();

        model.put("loginFailed", false);
        model.put("loginForm", new Form());

        return new ModelAndView("login");
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ModelAndView login(Map<String, Object> model, HttpSession session,
                              HttpServletRequest request, Form form)
    {
        if(UserPrincipal.getPrincipal(session) != null)
            return this.getTicketRedirect();

        Principal principal = this.authenticationService.authenticate(
                form.getUsername(), form.getPassword()
        );
        if(principal == null)
        {
            form.setPassword(null);
            model.put("loginFailed", true);
            model.put("loginForm", form);
            return new ModelAndView("login");
        }

        UserPrincipal.setPrincipal(session, principal);
        request.changeSessionId();
        return this.getTicketRedirect();
    }

    private ModelAndView getTicketRedirect()
    {
        return new ModelAndView(new RedirectView("/ticket/list", true, false));
    }

    public static class Form
    {
        private String username;
        private String password;

        public String getUsername()
        {
            return username;
        }

        public void setUsername(String username)
        {
            this.username = username;
        }

        public String getPassword()
        {
            return password;
        }

        public void setPassword(String password)
        {
            this.password = password;
        }
    }
}
