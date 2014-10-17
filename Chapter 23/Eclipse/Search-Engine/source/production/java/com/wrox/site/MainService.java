package com.wrox.site;

import com.wrox.site.entities.ForumPost;
import com.wrox.site.entities.User;
import com.wrox.site.repositories.SearchResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MainService
{
    User getUser(String username);
    Page<SearchResult<ForumPost>> search(String query, Pageable pageable);
    void save(ForumPost forumPost);
}
