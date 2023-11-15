package com.school.backend.service;

import com.school.backend.model.Student;
import com.school.backend.repository.ClassRepository;
import com.school.backend.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

  private final StudentRepository studentRepository;
  private final ClassRepository classRepository;

  public StudentService(StudentRepository studentRepository, ClassRepository classRepository) {
    this.studentRepository = studentRepository;
    this.classRepository = classRepository;
  }

  /**
   * Retrieves all students belonging to a class by its id.
   *
   * @param idClass The ID of the class for which to retrieve the students.
   * @return An Iterable of Student objects representing all students of the specified class.
   */
  public Iterable<Student> getAllStudentsByIdClass(final Long idClass) {
    return studentRepository.findAllByIdClass(idClass);
  }

  /**
   * Creates a new student and associates him with a class.
   *
   * @param student The Student object representing the new student to be created.
   * @param idClass The ID of the class with which the student should be associated.
   * @return The saved Student object if the creation is successful and the class exists, or null otherwise.
   */
  @Transactional
  public Student createOneStudent(Student student, final Long idClass) {
    if(!classRepository.existsById(idClass)) return null;
    student.setIdClass(idClass);
    student.setId((long) 0);
    return studentRepository.save(student);
  }

  /**
   * Deletes a student by his ID.
   *
   * @param idStudent The ID of the student to be deleted.
   * @return True if the student is successfully deleted, false the student does not exist.
   */
  @Transactional
  public boolean deleteStudentById(final Long idStudent) {
    if (!studentRepository.existsById(idStudent)) return false;
    studentRepository.deleteById(idStudent);
    return true;
  }
}
