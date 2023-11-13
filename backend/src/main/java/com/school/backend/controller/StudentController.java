package com.school.backend.controller;

import com.school.backend.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class StudentController {

  private final StudentService studentService;

  public StudentController(StudentService studentService) {
    this.studentService = studentService;
  }

  @CrossOrigin
  @DeleteMapping("/students/{idStudent}")
  public void deleteOne(@PathVariable final Long idStudent) {
    boolean studentFound = studentService.deleteStudentById(idStudent);
    if(!studentFound) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
  }
}
