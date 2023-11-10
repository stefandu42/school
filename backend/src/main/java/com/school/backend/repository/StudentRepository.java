package com.school.backend.repository;

import com.school.backend.model.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {

  void deleteByIdClass(Long idClass);

  Iterable<Student> findAllByIdClass(Long idClass);

}
