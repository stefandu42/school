package com.school.backend.service;

import com.school.backend.model.Class;
import com.school.backend.repository.ClassRepository;
import com.school.backend.repository.StudentRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ClassService {


  private final StudentRepository studentRepository;
  private final ClassRepository classRepository;

  public ClassService(StudentRepository studentRepository, ClassRepository classRepository) {
    this.studentRepository = studentRepository;
    this.classRepository = classRepository;
  }

  /**
   * Retrieves all classes.
   *
   * @return An Iterable representing all classes.
   */
  public Iterable<Class> getAll() {
    return classRepository.findAll();
  }


  /**
   * Retrieves a class by its ID.
   *
   * @param idClass The ID of the class to be retrieved.
   * @return An Optional containing the retrieved class, or an empty Optional if the class is not found.
   */
  public Optional<Class> getClassById(final Long idClass) {
    return classRepository.findById(idClass);
  }

  /**
   * Creates a new class.
   *
   * @param newClass The Class object representing the new class to be created.
   * @return The saved Class object if the creation is successful, or null if a class with the
   * same name already exists.
   */
  @Transactional
  public Class createOne(Class newClass) {
    newClass.setId((long) 0);
    newClass.setName(newClass.getName().trim());

    if(classRepository.existsByName(newClass.getName())) return null;
    return classRepository.save(newClass);
  }

  /**
   * Deletes a class by its ID and its students.
   *
   * @param id The ID of the class to be deleted.
   * @return True if the class and its students are successfully deleted, false otherwise.
   */
  @Transactional
  public boolean deleteOneById(final Long id) {
    if (!classRepository.existsById(id)) return false;
    studentRepository.deleteByIdClass(id);
    classRepository.deleteById(id);
    return true;
  }

}
