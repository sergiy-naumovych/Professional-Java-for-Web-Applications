package com.wrox.site;

import com.wrox.config.annotation.WebController;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@WebController
public class HomeController
{
    @Inject ApplicationEventPublisher publisher;

    @RequestMapping("")
    public String login(HttpServletRequest request)
    {
        this.publisher.publishEvent(new LoginEvent(request.getContextPath()));
        return "login";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request)
    {
        this.publisher.publishEvent(new LogoutEvent(request.getContextPath()));
        return "logout";
    }

    @RequestMapping("/ping")
    public ResponseEntity<String> ping()
    {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/plain;charset=UTF-8");
        return new ResponseEntity<>("ok", headers, HttpStatus.OK);
    }
}
