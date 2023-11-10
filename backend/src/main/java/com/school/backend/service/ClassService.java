package com.school.backend.service;

import com.school.backend.model.Class;
import com.school.backend.repository.ClassRepository;
import com.school.backend.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ClassService {


  private final StudentRepository studentRepository;
  private final ClassRepository classRepository;

  public ClassService(StudentRepository studentRepository, ClassRepository classRepository) {
    this.studentRepository = studentRepository;
    this.classRepository = classRepository;
  }

  public Iterable<Class> getAll() {
    return classRepository.findAll();
  }

  @Transactional
  public Class createOne(Class newClass) {
    newClass.setId((long) 0);
    newClass.setName(newClass.getName().trim());

    if(classRepository.existsByName(newClass.getName())) return null;
    Class savedClass = classRepository.save(newClass);
    return savedClass;
  }

  @Transactional
  public boolean deleteOneById(final Long id) {
    if (!classRepository.existsById(id)) return false;
    studentRepository.deleteByIdClass(id);
    classRepository.deleteById(id);
    return true;
  }

}
