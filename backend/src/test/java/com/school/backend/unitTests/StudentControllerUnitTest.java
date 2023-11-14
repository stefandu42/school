package com.school.backend.unitTests;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.school.backend.controller.StudentController;
import com.school.backend.model.Student;
import com.school.backend.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = StudentController.class)
public class StudentControllerUnitTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private StudentService studentService;

  private Student newStudent;

  @BeforeEach
  public void setUp() {
    newStudent = new Student();
    newStudent.setIdClass((long) 4);
    newStudent.setId((long) 2);
    newStudent.setFirstname("Jean");
    newStudent.setSurname("Jack");
  }


  // --------------------------- Delete one student by id ------------------------

  @Test
  @DisplayName("Test delete one student by id success")
  public void testDeleteOneById() throws Exception {
    doReturn(true).when(studentService).deleteStudentById(newStudent.getId());

    mockMvc.perform(delete("/students/"+newStudent.getId()))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("Test delete one student by id with a non existing id")
  public void testDeleteOneByIdWithNonExistingId() throws Exception {
    newStudent.setId((long) -5);

    mockMvc.perform(delete("/students/"+newStudent.getId()))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Test delete one student by id with a non existing student")
  public void testDeleteOneByIdWithNonExistingStudent() throws Exception {
    doReturn(false).when(studentService).deleteStudentById(newStudent.getId());

    mockMvc.perform(delete("/students/"+newStudent.getId()))
        .andExpect(status().isNotFound());
  }

}
