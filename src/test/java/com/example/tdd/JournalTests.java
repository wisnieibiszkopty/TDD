package com.example.tdd;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

// TODO add requests dtos

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = TddApplication.class
)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@TestPropertySource(locations = "classpath:tests.properties")
@AutoConfigureTestDatabase
public class JournalTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void whenValidInput_thenCreateJournal(){
        String journalJson = "{\"name\": \"Math Journal\"}";

        mvc.perform(post("/api/journals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(journalJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Math Journal")));
    }

    @Test
    public void whenValidInput_thenCreateStudent(){
        String studentJson = "{\"name\": \"John Doe\"}";

        mvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("John Doe")));
    }

    @Test
    public void givenJournalId_getStudents(){
        Long journalId = 1L;

        mvc.perform(get("/api/journals/" + journalId + "/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("John Doe")));
    }

    @Test
    public void givenMarkAndStudentId_addMarkToStudent_ThenReturnStudent(){
        Long studentId = 1L;
        Double mark = 5.0;

        String markJson = "{\"mark\": " + mark + "}";

        mvc.perform(post("/api/students/" + studentId + "/marks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(markJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.grades[0]", is(mark)));
    }

    @Test
    public void computeAverageGradeForAllStudents_thenReturnAverageGrades(){
        mvc.perform(get("/api/students/average"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.averageGrade", is(4.5)));
    }

}
