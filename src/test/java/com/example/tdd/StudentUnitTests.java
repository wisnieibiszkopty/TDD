package com.example.tdd;

import com.example.tdd.model.Journal;
import com.example.tdd.model.Student;
import com.example.tdd.repository.JournalRepository;
import com.example.tdd.repository.StudentRepository;
import com.example.tdd.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class StudentUnitTests {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private JournalRepository journalRepository;

    @InjectMocks
    private StudentService studentService;


    @Test
    public void givenStudentData_saveStudent_ThenReturnStudent(){
        var student = Student.builder().name("Kamil").build();
        when(studentRepository.save(student)).thenReturn(student);

        var result = studentService.createStudent(student);
        assertNotNull(result);
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    public void givenStudentIdAndJournalId_addStudentToJournal_ThenReturnJournal(){
        Long studentId = 1L;
        Long journalId = 2L;

        var journal = Journal.builder().id(journalId).name("Journal 1").build();
        var student = Student.builder().id(studentId).name("Kamil").build();

        when(journalRepository.findById(journalId)).thenReturn(Optional.of(journal));
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(journalRepository.save(journal)).thenReturn(Journal.builder()
                .id(journalId)
                .name("Journal 1")
                .students(List.of(student))
                .build());

        var result = studentService.addToJournal(studentId, journalId);

        assertNotNull(result);
        assertEquals(1, result.getStudents().size());
        assertTrue(result.getStudents().contains(student));
    }
}
