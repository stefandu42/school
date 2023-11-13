package com.school.backend.repository;

import com.school.backend.model.Class;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends CrudRepository<Class, Long> {


  boolean existsById(Long id);

  boolean existsByName(String name);
}
