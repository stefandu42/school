package com.school.backend.integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.backend.model.Class;
import com.school.backend.model.Student;
import com.school.backend.repository.ClassRepository;
import com.school.backend.repository.StudentRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StudentControllerIntegrationTest {


  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private ClassRepository classRepository;

  Class newClassMath;
  Student newStudentFranck;

  @BeforeAll
  public void setUp() {
    newClassMath = new Class();
    newStudentFranck = new Student();
  }

  @AfterAll
  public void afterAll() {
    this.studentRepository.deleteAll();
    this.classRepository.deleteAll();
  }

  @BeforeEach
  public void beforeEach() {
    newClassMath.setId((long) 0);
    newClassMath.setName("Math");

    newStudentFranck.setId((long) 0);
    newStudentFranck.setFirstname("Franck");
    newStudentFranck.setSurname("Dupont");
    newStudentFranck.setIdClass(newClassMath.getId());

    this.studentRepository.deleteAll();
    this.classRepository.deleteAll();
  }

  private void checkSizeClasses(int numberOfClassesExpected) throws Exception {
    mockMvc.perform(get("/classes"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(numberOfClassesExpected)));
  }

  private void checkSizeStudentsByClass(int numberOfStudentsExpected, long idClass) throws Exception {
    mockMvc.perform(get("/classes/"+idClass+"/students"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(numberOfStudentsExpected)));
  }

  private Class createOneClass(Class newClass) throws Exception {
    String responseContent = mockMvc.perform(post("/classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newClass)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name", is(newClass.getName())))
        .andExpect(jsonPath("$.id", greaterThan(newClass.getId().intValue())))
        .andReturn()
        .getResponse()
        .getContentAsString();

    return objectMapper.readValue(responseContent, Class.class);
  }

  private Student createOneStudent(Student newStudent, long idClass) throws Exception {
    String responseContent = mockMvc.perform(post("/classes/" + idClass + "/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newStudent)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.firstname", is(newStudent.getFirstname())))
        .andExpect(jsonPath("$.surname", is(newStudent.getSurname())))
        .andExpect(jsonPath("$.idClass", is(newStudent.getIdClass().intValue())))
        .andExpect(jsonPath("$.id", greaterThan(newStudent.getId().intValue())))
        .andReturn()
        .getResponse()
        .getContentAsString();

    return objectMapper.readValue(responseContent, Student.class);
  }

  // --------------------------- Delete one student ------------------------

  @Test
  @DisplayName("Test delete one student success")
  public void testDeleteOneSuccess() throws Exception {
    Class newClass = this.createOneClass(newClassMath);
    newStudentFranck.setIdClass(newClass.getId());
    Student newStudent = this.createOneStudent(newStudentFranck, newClass.getId());

    this.checkSizeStudentsByClass(1,newClass.getId());
    this.checkSizeClasses(1);

    mockMvc.perform(delete("/students/"+newStudent.getId()))
        .andExpect(status().isOk());

    this.checkSizeStudentsByClass(0,newClass.getId());
    this.checkSizeClasses(1);
  }

  @Test
  @DisplayName("Test delete one student with non existing id")
  public void testDeleteOneWithNonExistingId() throws Exception {
    Class newClass = this.createOneClass(newClassMath);
    newStudentFranck.setIdClass(newClass.getId());
    Student newStudent = this.createOneStudent(newStudentFranck, newClass.getId());

    this.checkSizeStudentsByClass(1,newClass.getId());
    this.checkSizeClasses(1);

    mockMvc.perform(delete("/students/"+newStudent.getId()))
        .andExpect(status().isOk());

    mockMvc.perform(delete("/students/"+newStudent.getId()))
        .andExpect(status().isNotFound());

    this.checkSizeStudentsByClass(0,newClass.getId());
    this.checkSizeClasses(1);
  }

  @Test
  @DisplayName("Test delete one student with negative id")
  public void testDeleteOneWithNegativeId() throws Exception {
    mockMvc.perform(delete("/students/"+newStudentFranck.getId()))
        .andExpect(status().isBadRequest());
  }

}
