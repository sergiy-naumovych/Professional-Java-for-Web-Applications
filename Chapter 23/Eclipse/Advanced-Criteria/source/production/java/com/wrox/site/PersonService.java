package com.wrox.site;

import com.wrox.site.entities.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonService
{
    void savePerson(Person person);
    Page<Person> searchPeople(SearchCriteria searchCriteria, Pageable pageable);
}
