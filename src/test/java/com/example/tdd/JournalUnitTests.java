package com.example.tdd;

import com.example.tdd.model.Journal;
import com.example.tdd.model.Student;
import com.example.tdd.repository.JournalRepository;
import com.example.tdd.service.JournalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JournalUnitTests {

    @Mock
    private JournalRepository journalRepository;

    @InjectMocks
    private JournalService journalService;

    @Test
    public void givenJournalData_saveJournal_ThenReturnJournal(){
        var journal = Journal.builder().name("Journal1").build();
        when(journalRepository.save(journal)).thenReturn(journal);

        var result = journalService.createJournal(journal);
        assertNotNull(result);
        verify(journalRepository, times(1)).save(journal);
    }

    @Test
    public void givenStudentIdAndJournalId_JournalNotFind_ThenThrowError(){
        Long studentId = 1L;
        Long journalId = 100L;

        when(journalRepository.findById(journalId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(JournalNotFoundException.class, () -> {
            journalService.getStudentFromJournal(studentId, journalId);
        });

        assertEquals("Journal not found", exception.getMessage());
    }

    @Test
    public void givenJournalId_getStudents_ThenReturnStudents(){
        Long journalId = 1L;
        Student student1 = new Student(1L, "John Doe");
        Student student2 = new Student(2L, "Jane Smith");
        List<Student> students = Arrays.asList(student1, student2);

        Journal journal = new Journal(journalId, "Math Journal");
        when(journalRepository.findById(journalId)).thenReturn(Optional.of(journal));

        when(studentRepository.findByJournalId(journalId)).thenReturn(students);

        List<Student> result = journalService.getStudentsFromJournal(journalId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(student1));
        assertTrue(result.contains(student2));
    }

    @Test
    public void givenJournalId_JournalNotFind_ThenThrowError(){
        Long journalId = 1L;

        when(journalRepository.findById(journalId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(JournalNotFoundException.class, () -> {
            journalService.getStudentsFromJournal(journalId);
        });

        assertEquals("Journal not found", exception.getMessage());
    }

}
