package com.wrox.site;

import com.wrox.config.annotation.WebController;
import org.springframework.web.bind.annotation.RequestMapping;

@WebController
public class HomeController
{
    @RequestMapping("/")
    public String index()
    {
        return "index";
    }
}
