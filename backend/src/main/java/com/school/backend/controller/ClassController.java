package com.school.backend.controller;

import com.school.backend.model.Class;
import java.util.Optional;
import com.school.backend.model.Student;
import com.school.backend.service.ClassService;
import com.school.backend.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/classes")
@CrossOrigin
public class ClassController {

  private final ClassService classService;
  private final StudentService studentService;

  public ClassController(ClassService classService, StudentService studentService) {
    this.classService = classService;
    this.studentService = studentService;
  }

  /**
   * Retrieves all classes.
   *
   * @return An Iterable of Class objects representing all existing classes.
   */
  @GetMapping
  public Iterable<Class> getAll() {
    return classService.getAll();
  }


  /**
   * Retrieves a class by its ID.
   *
   * @param idClass The ID of the class to retrieve.
   * @return The Class object corresponding to the provided ID.
   * @throws ResponseStatusException If the ID is invalid or the class is not found.
   */
  @GetMapping("/{id}")
  public Class getClassById(@PathVariable("id") final Long idClass) {
    if (idClass<=0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some fields are invalid");
    }
    Optional<Class> classFound = classService.getClassById(idClass);
    if(classFound.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Class not found");
    return classFound.get();
  }

  /**
   * Creates a new class.
   *
   * @param newClass The Class object representing the new class to be created.
   * @return A ResponseEntity containing the created Class object and HTTP status 201 (Created).
   * @throws ResponseStatusException If some fields of newClass object are invalid or the class already exists.
   */
  @PostMapping
  public ResponseEntity<Class> createOne(@RequestBody Class newClass) {
    if (newClass.getName() == null || newClass.getName().trim().isBlank()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some fields are invalid");
    }
    Class classCreated = classService.createOne(newClass);
    if (classCreated==null) throw new ResponseStatusException(HttpStatus.CONFLICT, "This class already exists");
    return new ResponseEntity<>(classCreated, HttpStatus.CREATED);
  }

  /**
   * Deletes a class by its ID.
   *
   * @param idClass The ID of the class to delete.
   * @throws ResponseStatusException If the ID is invalid or the class is not found.
   */
  @DeleteMapping("/{idClass}")
  public void deleteOneById(@PathVariable final Long idClass) {
    if (idClass<=0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some fields are invalid");
    }
    boolean classFound = classService.deleteOneById(idClass);
    if(!classFound) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Class not found");
  }

  /**
   * Retrieves all students belonging to a class by its ID.
   *
   * @param idClass The ID of the class for which to retrieve students.
   * @return An Iterable of Student objects representing all students belonging to the specified class.
   * @throws ResponseStatusException If the ID given is invalid.
   */
  @GetMapping("/{idClass}/students")
  public Iterable<Student> getAllStudentsByIdClass(@PathVariable final Long idClass) {
    if (idClass<=0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some fields are invalid");
    }
    return studentService.getAllStudentsByIdClass(idClass);
  }


  /**
   * Creates a new student and associates him with a class
   *
   * @param student The Student object representing the new student to be created.
   * @param idClass The ID of the class with which the student should be associated.
   * @return A ResponseEntity containing the created Student object and HTTP status 201 (Created).
   * @throws ResponseStatusException If some fields of student object given are invalid or the class is not found.
   */
  @PostMapping("/{idClass}/students")
  public ResponseEntity<Student> createOneStudent(@RequestBody Student student, @PathVariable Long idClass) {
    if (student.getFirstname() == null || student.getFirstname().trim().isBlank() ||
        student.getSurname() == null || student.getSurname().trim().isBlank() ||
        !student.getIdClass().equals(idClass) || idClass<=0
    ) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some fields are invalid");
    }
    Student newStudent = studentService.createOneStudent(student, idClass);
    if(newStudent==null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Class not found");
    return new ResponseEntity<>(newStudent, HttpStatus.CREATED);

  }
}
