package com.wrox.site;

import com.wrox.config.annotation.WebController;
import com.wrox.site.entities.ForumPost;
import com.wrox.site.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import java.util.Map;

@WebController
public class MainController
{
    @Inject MainService mainService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String form(Map<String, Object> model)
    {
        model.put("added", null);
        model.put("addForm", new PostForm());
        return "add";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String add(Map<String, Object> model, PostForm form)
    {
        User user = this.mainService.getUser(form.getUser());
        if(user == null)
            throw new IllegalArgumentException("User does not exist.");

        ForumPost post = new ForumPost();
        post.setUser(user);
        post.setTitle(form.getTitle());
        post.setBody(form.getBody());
        post.setKeywords(form.getKeywords());

        this.mainService.save(post);

        model.put("added", post.getId());
        model.put("addForm", new PostForm());
        return "add";
    }

    @RequestMapping(value = "search")
    public String search(Map<String, Object> model)
    {
        model.put("searchPerformed", false);
        model.put("searchForm", new SearchForm());

        return "search";
    }

    @RequestMapping(value = "search", params = "query")
    public String search(Map<String, Object> model, SearchForm form,
                         Pageable pageable)
    {
        if(form.getQuery() == null || form.getQuery().trim().length() == 0)
            model.put("searchPerformed", false);
        else
        {
            model.put("searchPerformed", true);
            model.put("results", this.mainService.search(
                    form.getQuery(), pageable)
            );
        }

        return "search";
    }

    public static class PostForm
    {
        private String user;

        private String title;

        private String body;

        private String keywords;

        public String getUser()
        {
            return this.user;
        }

        public void setUser(String user)
        {
            this.user = user;
        }

        public String getTitle()
        {
            return this.title;
        }

        public void setTitle(String title)
        {
            this.title = title;
        }

        public String getBody()
        {
            return this.body;
        }

        public void setBody(String body)
        {
            this.body = body;
        }

        public String getKeywords()
        {
            return this.keywords;
        }

        public void setKeywords(String keywords)
        {
            this.keywords = keywords;
        }
    }

    public static class SearchForm
    {
        private String query;

        public String getQuery()
        {
            return this.query;
        }

        public void setQuery(String query)
        {
            this.query = query;
        }
    }
}
