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

  public Iterable<Student> getAllStudentsByIdClass(final Long idClass) {
    return studentRepository.findAllByIdClass(idClass);
  }

  @Transactional
  public Student createOneStudent(Student student, final Long idClass) {
    if(!classRepository.existsById(idClass)) return null;
    student.setIdClass(idClass);
    student.setId((long) 0);
    return studentRepository.save(student);
  }

  @Transactional
  public boolean deleteStudentById(final Long idStudent) {
    if (!studentRepository.existsById(idStudent)) return false;
    studentRepository.deleteById(idStudent);
    return true;
  }
}
