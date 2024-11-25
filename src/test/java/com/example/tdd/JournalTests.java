package com.example.tdd;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    public void whenValidInput_thenCreateJournal() throws Exception {
        String journalJson = "{\"name\": \"Math Journal\"}";

        mvc.perform(post("/api/journals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(journalJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Math Journal")));
    }

    @Test
    public void whenValidInput_thenCreateStudent() throws Exception {
        String studentJson = "{\"name\": \"John Doe\"}";

        mvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("John Doe")));
    }

    @Test
    public void givenJournalId_getStudents() throws Exception {
        Long journalId = 1L;

        mvc.perform(get("/api/journals/" + journalId + "/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("John Doe")));
    }

    @Test
    public void givenMarkAndStudentId_addMarkToStudent_ThenReturnStudent() throws Exception {
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
    public void computeAverageGradeForAllStudents_thenReturnAverageGrades() throws Exception {
        mvc.perform(get("/api/students/average"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.averageGrade", is(4.5)));
    }

}
