package com.wrox.site.repositories;

import com.wrox.site.entities.ForumPost;
import org.springframework.data.repository.CrudRepository;

public interface ForumPostRepository extends CrudRepository<ForumPost, Long>,
        SearchableRepository<ForumPost>
{
}
