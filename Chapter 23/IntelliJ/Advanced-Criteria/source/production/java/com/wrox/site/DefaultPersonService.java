package com.wrox.site;

import com.wrox.site.entities.Person;
import com.wrox.site.repositories.PersonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
public class DefaultPersonService implements PersonService
{
    @Inject PersonRepository repository;

    @Override
    @Transactional
    public void savePerson(Person person)
    {
        this.repository.save(person);
    }

    @Override
    @Transactional
    public Page<Person> searchPeople(SearchCriteria searchCriteria, Pageable pageable)
    {
        return this.repository.search(searchCriteria, pageable);
    }
}
