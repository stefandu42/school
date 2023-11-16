package com.school.backend.repository;

import com.school.backend.model.Class;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends CrudRepository<Class, Long> {


  /**
   * Checks if a class with the specified ID exists.
   *
   * @param id The ID of the class to check.
   * @return True if a class with the given ID exists, false otherwise.
   */
  boolean existsById(Long id);

  /**
   * Checks if a class with the specified name exists.
   *
   * @param name The name of the class to check.
   * @return True if a class with the given name exists, false otherwise.
   */
  boolean existsByName(String name);
}
