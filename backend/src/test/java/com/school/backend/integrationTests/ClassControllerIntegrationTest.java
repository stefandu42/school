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
public class ClassControllerIntegrationTest {

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
    String responseContent = mockMvc.perform(post("/classes/"+idClass+"/students")
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


  // --------------------------- Get all classes ------------------------

  @Test
  @DisplayName("Test get all classes success")
  public void testGetAllSuccess() throws Exception {
    Class newClass = this.createOneClass(newClassMath);

    mockMvc.perform(get("/classes"))
        .andExpect(status().isOk())
        .andExpect(content().json("["+objectMapper.writeValueAsString(newClass)+"]"));
    this.checkSizeClasses(1);
  }

  // --------------------------- Get class by id ------------------------

  @Test
  @DisplayName("Test get one classe by id success")
  public void testOneClassByIdSuccess() throws Exception {
    Class newClass = this.createOneClass(newClassMath);

    mockMvc.perform(get("/classes/"+newClass.getId()))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(newClass)));
    this.checkSizeClasses(1);
  }

  @Test
  @DisplayName("Test get one classe by id with non existing id")
  public void testOneClassByIdWithNonExistingId() throws Exception {

    mockMvc.perform(get("/classes/"+1000))
        .andExpect(status().isNotFound());
    this.checkSizeClasses(0);
  }

  @Test
  @DisplayName("Test get one classe by id with negative id")
  public void testOneClassByIdWithNegativeId() throws Exception {

    mockMvc.perform(get("/classes/"+newClassMath.getId()))
        .andExpect(status().isBadRequest());
    this.checkSizeClasses(0);
  }

  // --------------------------- Create one class ------------------------

  @Test
  @DisplayName("Test create one class success")
  public void testCreateOneSuccess() throws Exception {
    this.createOneClass(newClassMath);

    this.checkSizeClasses(1);
  }

  @Test
  @DisplayName("Test create one class with null name")
  public void testCreateOneWithNullName() throws Exception {
    newClassMath.setName(null);
    mockMvc.perform(post("/classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newClassMath)))
        .andExpect(status().isBadRequest());

    this.checkSizeClasses(0);
  }

  @Test
  @DisplayName("Test create one class with blank name")
  public void testCreateOneWithBlankName() throws Exception {
    newClassMath.setName("");
    mockMvc.perform(post("/classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newClassMath)))
        .andExpect(status().isBadRequest());

    this.checkSizeClasses(0);
  }

  @Test
  @DisplayName("Test create one class conflict")
  public void testCreateOneConflict() throws Exception {
    this.createOneClass(newClassMath);

    this.checkSizeClasses(1);

    mockMvc.perform(post("/classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newClassMath)))
        .andExpect(status().isConflict());

    this.checkSizeClasses(1);
  }

  // --------------------------- Create one student ------------------------

  @Test
  @DisplayName("Test create one student success")
  public void testCreateOneStudentSuccess() throws Exception {
    newClassMath.setName("Math");
    Class newClass = this.createOneClass(newClassMath);

    newStudentFranck.setIdClass(newClass.getId());
    this.checkSizeClasses(1);

    this.createOneStudent(newStudentFranck, newClass.getId());

    this.checkSizeStudentsByClass(1, newClass.getId());
  }

  @Test
  @DisplayName("Test create one student with non existing class")
  public void testCreateOneStudentWithNonExistingClass() throws Exception {
    newStudentFranck.setIdClass((long) 1000);

    mockMvc.perform(post("/classes/"+newStudentFranck.getIdClass()+"/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newStudentFranck)))
        .andExpect(status().isNotFound());

    this.checkSizeStudentsByClass(0, newStudentFranck.getIdClass());
  }


  @Test
  @DisplayName("Test create one student with null firstname")
  public void testCreateOneStudentWithNullFirstname() throws Exception {
    newStudentFranck.setFirstname(null);

    mockMvc.perform(post("/classes/"+newStudentFranck.getIdClass()+"/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newStudentFranck)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Test create one student with null surname")
  public void testCreateOneStudentWithNullSurname() throws Exception {
    newStudentFranck.setSurname(null);

    mockMvc.perform(post("/classes/"+newStudentFranck.getIdClass()+"/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newStudentFranck)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Test create one student with blank firstname")
  public void testCreateOneStudentWithBlankFirstname() throws Exception {
    newStudentFranck.setFirstname("");

    mockMvc.perform(post("/classes/"+newStudentFranck.getIdClass()+"/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newStudentFranck)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Test create one student with blank surname")
  public void testCreateOneStudentWithBlankSurname() throws Exception {
    newStudentFranck.setSurname("");

    mockMvc.perform(post("/classes/"+newStudentFranck.getIdClass()+"/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newStudentFranck)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Test create one student with different id class")
  public void testCreateOneStudentWithDifferentIdClass() throws Exception {
    newClassMath.setId((long) 15);
    newStudentFranck.setIdClass((long) 10);

    mockMvc.perform(post("/classes/"+newClassMath.getId()+"/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newStudentFranck)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Test create one student with negative id class")
  public void testCreateOneStudentWithNegativeIdClass() throws Exception {

    mockMvc.perform(post("/classes/"+newClassMath.getId()+"/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newStudentFranck)))
        .andExpect(status().isBadRequest());
  }


  // --------------------------- Delete one class by id ------------------------

  @Test
  @DisplayName("Test delete one class success")
  public void testDeleteOneSuccess() throws Exception {
    Class newClass = this.createOneClass(newClassMath);
    newStudentFranck.setIdClass(newClass.getId());
    this.createOneStudent(newStudentFranck, newClass.getId());

    this.checkSizeStudentsByClass(1,newClass.getId());
    this.checkSizeClasses(1);

    mockMvc.perform(delete("/classes/"+newClass.getId()))
        .andExpect(status().isOk());

    this.checkSizeStudentsByClass(0,newClass.getId());
    this.checkSizeClasses(0);
  }

  @Test
  @DisplayName("Test delete one class with non existing id")
  public void testDeleteOneWithNonExistingId() throws Exception {
    Class newClass = this.createOneClass(newClassMath);
    newStudentFranck.setIdClass(newClass.getId());
    this.createOneStudent(newStudentFranck, newClass.getId());

    this.checkSizeStudentsByClass(1,newClass.getId());
    this.checkSizeClasses(1);

    mockMvc.perform(delete("/classes/"+newClass.getId()))
        .andExpect(status().isOk());

    mockMvc.perform(delete("/classes/"+newClass.getId()))
        .andExpect(status().isNotFound());

    this.checkSizeStudentsByClass(0,newClass.getId());
    this.checkSizeClasses(0);
  }

  @Test
  @DisplayName("Test delete one class success")
  public void testDeleteOneWithNegativeIdClass() throws Exception {

    mockMvc.perform(delete("/classes/"+newClassMath.getId()))
        .andExpect(status().isBadRequest());

    this.checkSizeClasses(0);
  }

  // --------------------------- Get all students by id class ------------------------


  @Test
  @DisplayName("Test get all students by id class success")
  public void testGetAllStudentsByIdClassSuccess() throws Exception {
    Class newClass = this.createOneClass(newClassMath);
    newStudentFranck.setIdClass(newClass.getId());
    Student newStudent = this.createOneStudent(newStudentFranck, newClass.getId());

    this.checkSizeStudentsByClass(1,newClass.getId());
    this.checkSizeClasses(1);

    mockMvc.perform(get("/classes/"+newClass.getId()+"/students"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json("["+objectMapper.writeValueAsString(newStudent)+"]"));
  }

  @Test
  @DisplayName("Test get all students by id class with negative id class")
  public void testGetAllStudentsByIdClassWithNegativeIdClass() throws Exception {
    mockMvc.perform(get("/classes/"+newClassMath.getId()+"/students"))
        .andExpect(status().isBadRequest());
  }
}
