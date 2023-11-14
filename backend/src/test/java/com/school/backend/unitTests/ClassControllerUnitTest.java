package com.school.backend.unitTests;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.backend.controller.ClassController;
import com.school.backend.model.Class;
import com.school.backend.model.Student;
import com.school.backend.service.ClassService;
import com.school.backend.service.StudentService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ClassController.class)
public class ClassControllerUnitTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private ClassService classService;

  @MockBean
  private StudentService studentService;

  private Class newClass;
  private Student newStudent;

  @BeforeEach
  public void setUp() {
    newClass = new Class();
    newClass.setId((long) 0);
    newClass.setName("Math");

    newStudent = new Student();
    newStudent.setIdClass(newClass.getId());
    newStudent.setId((long) 0);
    newStudent.setFirstname("Jean");
    newStudent.setSurname("Jack");
  }

  // --------------------------- Get all classes ------------------------

  @Test
  @DisplayName("Test getting all classes")
  public void testGetAllClasses() throws Exception {
    List<Class> getAllClassesResult = new ArrayList<Class>();
    doReturn(getAllClassesResult).when(classService).getAll();

    mockMvc.perform(get("/classes"))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(getAllClassesResult)));
  }

  // --------------------------- Create one class ------------------------

  @Test
  @DisplayName("Test create one class success")
  public void testCreateOne() throws Exception {
    doReturn(newClass).when(classService).createOne(newClass);

    mockMvc.perform(post("/classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newClass)))
        .andExpect(status().isCreated())
        .andExpect(content().json(objectMapper.writeValueAsString(newClass)));
  }

  @Test
  @DisplayName("Test create one class with null name")
  public void testCreateOneWithNullName() throws Exception {
    newClass.setName(null);
    doReturn(newClass).when(classService).createOne(newClass);

    mockMvc.perform(post("/classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newClass)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Test create one class with blank name")
  public void testCreateOneWithNameBlank() throws Exception {
    newClass.setName("");
    doReturn(newClass).when(classService).createOne(newClass);

    mockMvc.perform(post("/classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newClass)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Test create one class with an existing name")
  public void testCreateOneWithAnExistingName() throws Exception {
    doReturn(null).when(classService).createOne(newClass);

    mockMvc.perform(post("/classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newClass)))
        .andExpect(status().isConflict());
  }

  @Test
  @DisplayName("Test create one class with an empty body")
  public void testCreateOneWithEmptyBody() throws Exception {
    mockMvc.perform(post("/classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(new Class())))
        .andExpect(status().isBadRequest());
  }

  // --------------------------- Delete one class by id ------------------------

  @Test
  @DisplayName("Test delete one class by id success")
  public void testDeleteOneById() throws Exception {
    newClass.setId((long) 2);
    doReturn(true).when(classService).deleteOneById(newClass.getId());

    mockMvc.perform(delete("/classes/"+newClass.getId()))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("Test delete one class by id with a non existing id")
  public void testDeleteOneByIdWithNonExistingId() throws Exception {
    newClass.setId((long) -5);

    mockMvc.perform(delete("/classes/"+newClass.getId()))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Test delete one class by id with a non existing class")
  public void testDeleteOneByIdWithNonExistingClass() throws Exception {
    newClass.setId((long) 4);
    doReturn(false).when(classService).deleteOneById(newClass.getId());

    mockMvc.perform(delete("/classes/"+newClass.getId()))
        .andExpect(status().isNotFound());
  }

  // --------------------------- Get all students by id class ------------------------

  @Test
  @DisplayName("Test getting all students by id class")
  public void testGetAllStudentsByIdClass() throws Exception {
    newClass.setId((long) 5);
    List<Student> getAllStudentsResult = new ArrayList<Student>();
    doReturn(getAllStudentsResult).when(studentService).getAllStudentsByIdClass(newClass.getId());

    mockMvc.perform(get("/classes/"+newClass.getId()+"/students"))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(getAllStudentsResult)));
  }

  @Test
  @DisplayName("Test getting all students by id class with non existing class")
  public void testGetAllStudentsByIdClassWithNonExistingClass() throws Exception {
    newClass.setId((long) -5);

    mockMvc.perform(get("/classes/"+newClass.getId()+"/students"))
        .andExpect(status().isBadRequest());
  }


  // --------------------------- Create one student ------------------------


  @Test
  @DisplayName("Test create one student success")
  public void testCreateOneStudent() throws Exception {
    newClass.setId((long) 5);
    newStudent.setIdClass(newClass.getId());
    doReturn(newStudent).when(studentService).createOneStudent(newStudent, newClass.getId());

    mockMvc.perform(post("/classes/"+newStudent.getIdClass()+"/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newStudent)))
        .andExpect(status().isCreated())
        .andExpect(content().json(objectMapper.writeValueAsString(newStudent)));
  }

  @Test
  @DisplayName("Test create one student with different id class")
  public void testCreateOneStudentWithDifferentIdClass() throws Exception {
    doReturn(newStudent).when(studentService).createOneStudent(newStudent, newClass.getId());

    mockMvc.perform(post("/classes/"+1000+"/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newStudent)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Test create one student with negative id class")
  public void testCreateOneStudentWithNegativeIdClass() throws Exception {
    long id = -5;
    newStudent.setIdClass(id);
    newClass.setId(id);
    doReturn(newStudent).when(studentService).createOneStudent(newStudent, newClass.getId());

    mockMvc.perform(post("/classes/"+id+"/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newStudent)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Test create one student with null firstname")
  public void testCreateOneStudentWithNullFirstname() throws Exception {
    newStudent.setFirstname(null);
    doReturn(newStudent).when(studentService).createOneStudent(newStudent, newClass.getId());

    mockMvc.perform(post("/classes/"+newClass.getId()+"/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newStudent)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Test create one student with null surname")
  public void testCreateOneStudentWithNullSurname() throws Exception {
    newStudent.setSurname(null);
    doReturn(newStudent).when(studentService).createOneStudent(newStudent, newClass.getId());

    mockMvc.perform(post("/classes/"+newClass.getId()+"/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newStudent)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Test create one student with blank surname")
  public void testCreateOneStudentWithBlankSurname() throws Exception {
    newStudent.setSurname(" ");
    doReturn(newStudent).when(studentService).createOneStudent(newStudent, newClass.getId());

    mockMvc.perform(post("/classes/"+newClass.getId()+"/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newStudent)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Test create one student with blank firstname")
  public void testCreateOneStudentWithBlankFirstname() throws Exception {
    newStudent.setFirstname(" ");
    doReturn(newStudent).when(studentService).createOneStudent(newStudent, newClass.getId());

    mockMvc.perform(post("/classes/"+newClass.getId()+"/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newStudent)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Test create one student with non existing class")
  public void testCreateOneStudentWithNonExistingClass() throws Exception {
    newClass.setId((long) 1);
    newStudent.setIdClass(newClass.getId());
    doReturn(null).when(studentService).createOneStudent(newStudent, newClass.getId());

    mockMvc.perform(post("/classes/"+newClass.getId()+"/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newStudent)))
        .andExpect(status().isNotFound());
  }

  // --------------------------- Get class by id ------------------------

  @Test
  @DisplayName("Test get class by id success")
  public void testGetClassByIdSuccess() throws Exception {
    newClass.setId((long) 5);
    doReturn(Optional.ofNullable(newClass)).when(classService).getClassById(newClass.getId());

    mockMvc.perform(get("/classes/"+newClass.getId()))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(newClass)));
  }

  @Test
  @DisplayName("Test get class by id with non existing class")
  public void testGetClassByIdWithNonExistingClass() throws Exception {

    mockMvc.perform(get("/classes/"+newClass.getId()))
        .andExpect(status().isBadRequest());
  }

}
