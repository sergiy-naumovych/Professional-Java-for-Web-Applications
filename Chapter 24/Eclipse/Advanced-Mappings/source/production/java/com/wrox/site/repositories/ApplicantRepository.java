package com.wrox.site.repositories;

import com.wrox.site.entities.Applicant;
import org.springframework.data.repository.CrudRepository;

public interface ApplicantRepository extends CrudRepository<Applicant, Long>
{
}
