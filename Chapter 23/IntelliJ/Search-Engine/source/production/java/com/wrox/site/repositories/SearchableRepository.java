package com.wrox.site.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchableRepository<T>
{
    Page<SearchResult<T>> search(String query, Pageable pageable);
}
