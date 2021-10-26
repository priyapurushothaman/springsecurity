package com.hackerrank.springsecurity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackerrank.springsecurity.dto.Course;
import com.hackerrank.springsecurity.dto.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
class SpringSecurityTest {
    ObjectMapper mapper;
    @Autowired
    private MockMvc mockMvc;
    private Course course;
    private Student student;

    @BeforeEach
    public void contextLoads() {
        course = new Course(123, "Basic Mathematics", "Tom", "22B", null);
        student = new Student(100, "Jerry", "Mouse");
        mapper = new ObjectMapper();
    }

    @Test
    public void testAddCourseWithAdminCredentials() throws Exception {
        mockMvc.perform(post("/course")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(course))
                .header("Authorization", "Basic amFuZV9kb2U6YWRtaW5fcGFzc3dvcmQ="))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Added"));
    }

    @Test
    public void testAddCourseWithStudentCredentials() throws Exception {
        mockMvc.perform(post("/course")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(course))
                .header("Authorization", "Basic am9obl9kb2U6c3R1ZGVudF9wYXNzd29yZA=="))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.FORBIDDEN.value()))
                .andExpect(jsonPath("$.message").value("Authorization Failure-This user does not have the sufficient level of access"));
    }

    @Test
    public void testAddCourseWithIncorrectCredentials() throws Exception {
        mockMvc.perform(post("/course")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(course))
                .header("Authorization", "Basic am9uX2RvZTpzdG50X3Bhc3N3b3Jk"))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.UNAUTHORIZED.value()))
                .andExpect(jsonPath("$.message").value("Authentication Failure-The user name and password combination is incorrect"));
    }

    @Test
    public void testAddStudentToCourseWithStudentCredentials() throws Exception {
        mockMvc.perform(post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(student))
                .header("Authorization", "Basic am9obl9kb2U6c3R1ZGVudF9wYXNzd29yZA=="))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Added"));
    }

    @Test
    public void testAddStudentToCourseWithAdminCredentials() throws Exception {
        mockMvc.perform(post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(student))
                .header("Authorization", "Basic amFuZV9kb2U6YWRtaW5fcGFzc3dvcmQ="))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Added"));

    }

    @Test
    public void testAddStudentToCourseWithIncorrectCredentials() throws Exception {
        mockMvc.perform(post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(student))
                .header("Authorization", "Basic amFlX2RvZTphZG1pX3Bhc3N3b3Jk"))
                .andDo(print())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.UNAUTHORIZED.value()))
                .andExpect(jsonPath("$.message").value("Authentication Failure-The user name and password combination is incorrect"));

    }

    @Test
    public void testGetCourseDetailsWithNoCredentials() throws Exception {
        mockMvc.perform(get("/course"))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Courses"));
    }
}
