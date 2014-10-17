package com.wrox.site;

import com.wrox.site.entities.ForumPost;
import com.wrox.site.entities.User;
import com.wrox.site.repositories.ForumPostRepository;
import com.wrox.site.repositories.SearchResult;
import com.wrox.site.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
public class DefaultMainService implements MainService
{
    @Inject UserRepository userRepository;
    @Inject ForumPostRepository forumPostRepository;

    @Override
    @Transactional
    public User getUser(String username)
    {
        return this.userRepository.getByUsername(username);
    }

    @Override
    @Transactional
    public Page<SearchResult<ForumPost>> search(String query, Pageable pageable)
    {
        return this.forumPostRepository.search(query, pageable);
    }

    @Override
    @Transactional
    public void save(ForumPost forumPost)
    {
        this.forumPostRepository.save(forumPost);
    }
}
