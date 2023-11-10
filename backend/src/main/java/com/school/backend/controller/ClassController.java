package com.school.backend.controller;

import com.school.backend.model.Class;
import com.school.backend.model.Student;
import com.school.backend.service.ClassService;
import com.school.backend.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ClassController {

  private final ClassService classService;
  private final StudentService studentService;

  public ClassController(ClassService classService, StudentService studentService) {
    this.classService = classService;
    this.studentService = studentService;
  }

  @GetMapping("/classes")
  public Iterable<Class> getAll() {
    return classService.getAll();
  }

  @PostMapping("/classes")
  public ResponseEntity<Class> createOne(@RequestBody Class newClass) {
    if (newClass.getName() == null || newClass.getName().trim().isBlank()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some fields are invalid");
    }
    Class classCreated = classService.createOne(newClass);
    if (classCreated==null) throw new ResponseStatusException(HttpStatus.CONFLICT, "This class already exists");
    return new ResponseEntity<>(classCreated, HttpStatus.CREATED);
  }

  @DeleteMapping("/classes/{idClass}")
  public void deleteOneById(@PathVariable final Long idClass) {
    boolean classFound = classService.deleteOneById(idClass);
    if(!classFound) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Class not found");
  }

  @GetMapping("/classes/{idClass}/students")
  public Iterable<Student> getAllStudentsByIdClass(@PathVariable final Long idClass) {
    return studentService.getAllStudentsByIdClass(idClass);
  }

  @PostMapping("/classes/{idClass}/students")
  public ResponseEntity<Student> createOneStudent(@RequestBody Student student, @PathVariable Long idClass) {
    if (student.getFirstname() == null || student.getFirstname().trim().isBlank() ||
        student.getSurname() == null || student.getSurname().trim().isBlank()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some fields are invalid");
    }
    Student newStudent = studentService.createOneStudent(student, idClass);
    if(newStudent==null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Class not found");
    return new ResponseEntity<>(newStudent, HttpStatus.CREATED);

  }
}
