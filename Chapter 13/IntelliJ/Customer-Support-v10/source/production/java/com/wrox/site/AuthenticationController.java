package com.wrox.site;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Hashtable;
import java.util.Map;

@Controller
public class AuthenticationController
{
    private static final Logger log = LogManager.getLogger();
    private static final Map<String, String> userDatabase = new Hashtable<>();

    static {
        userDatabase.put("Nicholas", "password");
        userDatabase.put("Sarah", "drowssap");
        userDatabase.put("Mike", "wordpass");
        userDatabase.put("John", "green");
    }

    @RequestMapping("logout")
    public View logout(HttpSession session)
    {
        if(log.isDebugEnabled())
            log.debug("User {} logged out.", session.getAttribute("username"));
        session.invalidate();

        return new RedirectView("/login", true, false);
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public ModelAndView login(Map<String, Object> model, HttpSession session)
    {
        if(session.getAttribute("username") != null)
            return this.getTicketRedirect();

        model.put("loginFailed", false);
        model.put("loginForm", new Form());

        return new ModelAndView("login");
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ModelAndView login(Map<String, Object> model, HttpSession session,
                              HttpServletRequest request, Form form)
    {
        if(session.getAttribute("username") != null)
            return this.getTicketRedirect();

        if(form.getUsername() == null || form.getPassword() == null ||
                !userDatabase.containsKey(form.getUsername()) ||
                !form.getPassword().equals(userDatabase.get(form.getUsername())))
        {
            log.warn("Login failed for user {}.", form.getUsername());
            form.setPassword(null);
            model.put("loginFailed", true);
            model.put("loginForm", form);
            return new ModelAndView("login");
        }

        log.debug("User {} successfully logged in.", form.getUsername());
        session.setAttribute("username", form.getUsername());
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
