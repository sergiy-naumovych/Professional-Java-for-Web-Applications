package com.wrox.site;

import com.wrox.config.annotation.WebController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@WebController
public class MainController
{
    @RequestMapping({ "/signup", "/about", "/policies" })
    public String unprotectedUris()
    {
        return "unprotected";
    }

    @RequestMapping({ "/secure/*" })
    public String securePage()
    {
        return "secure";
    }

    @RequestMapping({ "/admin/*" })
    public String adminPage()
    {
        return "admin";
    }

    @RequestMapping(value = { "/login" }, method = RequestMethod.GET)
    public String login()
    {
        return "login";
    }
}
