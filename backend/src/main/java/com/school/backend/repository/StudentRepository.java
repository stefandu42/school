package com.school.backend.repository;

import com.school.backend.model.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {

  /**
   * Deletes all students belonging to the specified class ID.
   *
   * @param idClass The ID of the class whose students should be deleted.
   */
  void deleteByIdClass(Long idClass);

  /**
   * Retrieves all students belonging to the specified class ID.
   *
   * @param idClass The ID of the class for which to retrieve students.
   * @return An Iterable of Student objects representing all students belonging to the specified class.
   */
  Iterable<Student> findAllByIdClass(Long idClass);

}
