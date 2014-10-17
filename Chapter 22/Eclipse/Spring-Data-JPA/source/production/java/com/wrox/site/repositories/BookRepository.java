package com.wrox.site.repositories;

import com.wrox.site.entities.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long>
{
    Book getOneByIsbn(String isbn);
}
