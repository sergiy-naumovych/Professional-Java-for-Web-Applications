package com.wrox.site;

import com.wrox.site.entities.Author;
import com.wrox.site.entities.Book;
import com.wrox.site.entities.Publisher;

import java.util.List;

public interface BookManager
{
    List<Author> getAuthors();

    List<Book> getBooks();

    List<Publisher> getPublishers();

    void saveAuthor(Author author);

    void saveBook(Book book);

    void savePublisher(Publisher publisher);
}
