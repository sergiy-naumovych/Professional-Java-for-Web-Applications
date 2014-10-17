package com.wrox.site.repositories;

import com.wrox.site.entities.Resume;
import org.springframework.data.repository.CrudRepository;

public interface ResumeRepository extends CrudRepository<Resume, Long>
{
}
