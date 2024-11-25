package com.example.tdd;

import com.example.tdd.dto.JournalCreateDto;
import com.example.tdd.exception.JournalNotFoundException;
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
        var journalDto = new JournalCreateDto("Journal1");
        var journal = Journal.builder().id(1L).name(journalDto.name()).build();
        when(journalRepository.save(journal)).thenReturn(journal);

        var result = journalService.createJournal(journalDto);
        assertNotNull(result);
        verify(journalRepository, times(1)).save(journal);
    }

    @Test
    public void givenJournalId_getStudents_ThenReturnStudents(){
        Long journalId = 1L;
        var student1 = Student.builder().id(1L).name("John doe").build();
        var student2 = Student.builder().id(2L).name("Jane Smith").build();
        List<Student> students = Arrays.asList(student1, student2);

        Journal journal = Journal.builder().id(2L).name("Journal10").students(students).build();
        when(journalRepository.findById(journalId)).thenReturn(Optional.of(journal));

        List<Student> result = journalService.getStudents(journalId);

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
            journalService.getStudents(journalId);
        });

        assertEquals("Journal not found", exception.getMessage());
    }

}
