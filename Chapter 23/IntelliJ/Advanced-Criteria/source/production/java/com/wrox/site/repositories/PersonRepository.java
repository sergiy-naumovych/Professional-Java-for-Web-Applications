package com.wrox.site.repositories;

import com.wrox.site.entities.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long>,
        SearchableRepository<Person>
{
}

