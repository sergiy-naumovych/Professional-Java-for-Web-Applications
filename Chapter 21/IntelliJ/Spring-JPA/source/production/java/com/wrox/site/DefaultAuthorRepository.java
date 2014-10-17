package com.wrox.site;

import com.wrox.site.entities.Author;
import org.springframework.stereotype.Repository;

@Repository
public class DefaultAuthorRepository extends GenericJpaRepository<Long, Author>
    implements AuthorRepository
{

}
