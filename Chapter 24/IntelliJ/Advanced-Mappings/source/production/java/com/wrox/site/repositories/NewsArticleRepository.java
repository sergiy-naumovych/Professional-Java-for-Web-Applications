package com.wrox.site.repositories;

import com.wrox.site.entities.NewsArticle;
import org.springframework.data.repository.CrudRepository;

public interface NewsArticleRepository extends CrudRepository<NewsArticle, Long>
{
}
