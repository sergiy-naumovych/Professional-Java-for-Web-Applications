package com.wrox.site;

import com.wrox.site.entities.Book;

public interface BookRepository extends GenericRepository<Long, Book>
{
    Book getByIsbn(String isbn);
}
