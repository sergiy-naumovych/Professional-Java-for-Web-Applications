package com.wrox.site;

import com.wrox.site.entities.Book;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class DefaultBookRepository extends GenericJpaRepository<Long, Book>
        implements BookRepository
{
    @Override
    public Book getByIsbn(String isbn)
    {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> query = builder.createQuery(this.entityClass);
        Root<Book> root = query.from(this.entityClass);

        return this.entityManager.createQuery(
                query.select(root).where(builder.equal(root.get("isbn"), isbn))
        ).getSingleResult();
    }
}
