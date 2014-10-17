package com.wrox.site;

import com.wrox.site.entities.Author;
import com.wrox.site.entities.Book;
import com.wrox.site.entities.Publisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultBookManager implements BookManager
{
    @Inject AuthorRepository authorRepository;
    @Inject BookRepository bookRepository;
    @Inject PublisherRepository publisherRepository;

    @Override
    @Transactional
    public List<Author> getAuthors()
    {
        return this.toList(this.authorRepository.getAll());
    }

    @Override
    @Transactional
    public List<Book> getBooks()
    {
        return this.toList(this.bookRepository.getAll());
    }

    @Override
    @Transactional
    public List<Publisher> getPublishers()
    {
        return this.toList(this.publisherRepository.getAll());
    }

    private <E> List<E> toList(Iterable<E> i)
    {
        List<E> list = new ArrayList<>();
        i.forEach(list::add);
        return list;
    }

    @Override
    @Transactional
    public void saveAuthor(Author author)
    {
        if(author.getId() < 1)
            this.authorRepository.add(author);
        else
            this.authorRepository.update(author);
    }

    @Override
    @Transactional
    public void saveBook(Book book)
    {
        if(book.getId() < 1)
            this.bookRepository.add(book);
        else
            this.bookRepository.update(book);
    }

    @Override
    @Transactional
    public void savePublisher(Publisher publisher)
    {
        if(publisher.getId() < 1)
            this.publisherRepository.add(publisher);
        else
            this.publisherRepository.update(publisher);
    }
}
