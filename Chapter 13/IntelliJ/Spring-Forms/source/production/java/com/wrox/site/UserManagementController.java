package com.wrox.site;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Hashtable;
import java.util.Map;

@Controller
public class UserManagementController
{
    private final Map<Long, User> userDatabase = new Hashtable<>();
    private volatile long userIdSequence = 1L;

    @RequestMapping(value = "user/list", method = RequestMethod.GET)
    public String displayUsers(Map<String, Object> model)
    {
        model.put("userList", this.userDatabase.values());
        return "user/list";
    }

    @RequestMapping(value = "user/add", method = RequestMethod.GET)
    public String createUser(Map<String, Object> model)
    {
        model.put("userForm", new UserForm());
        return "user/add";
    }

    @RequestMapping(value = "user/add", method = RequestMethod.POST)
    public View createUser(UserForm form)
    {
        User user = new User();
        user.setUserId(this.getNextUserId());
        user.setUsername(form.getUsername());
        user.setName(form.getName());
        this.userDatabase.put(user.getUserId(), user);

        return new RedirectView("/user/list", true, false);
    }

    @RequestMapping(value = "user/edit/{userId}", method = RequestMethod.GET)
    public String editUser(Map<String, Object> model,
                           @PathVariable("userId") long userId)
    {
        User user = this.userDatabase.get(userId);
        UserForm form = new UserForm();
        form.setUsername(user.getUsername());
        form.setName(user.getName());
        model.put("userForm", form);

        return "user/edit";
    }

    @RequestMapping(value = "user/edit/{userId}", method = RequestMethod.POST)
    public View editUser(UserForm form, @PathVariable("userId") long userId)
    {
        User user = this.userDatabase.get(userId);
        user.setUsername(form.getUsername());
        user.setName(form.getName());

        return new RedirectView("/user/list", true, false);
    }

    private synchronized long getNextUserId()
    {
        return this.userIdSequence++;
    }
}
