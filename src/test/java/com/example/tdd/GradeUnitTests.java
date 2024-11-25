package com.example.tdd;

import com.example.tdd.exception.GradesNotFoundException;
import com.example.tdd.exception.StudentNotFoundException;
import com.example.tdd.model.Grade;
import com.example.tdd.model.Student;
import com.example.tdd.repository.GradeRepository;
import com.example.tdd.repository.StudentRepository;
import com.example.tdd.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class GradeUnitTests {

    @Mock
    private GradeRepository gradeRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    public void givenMarkAndStudentId_addMarkToStudent_ThenReturnStudent(){
        Long studentId = 1L;
        Double mark = 5.0;
        Student student = Student.builder().id(studentId).name("Kamil").build();
        Grade grade = Grade.builder().value(3.0).id(1L).build();

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(gradeRepository.save(grade)).thenReturn(grade);

        Student result = studentService.addGradeToStudent(studentId, mark);

        assertNotNull(result);
        assertEquals(1, result.getGrades().size());
        assertTrue(result.getGrades().contains(grade));
    }

    @Test
    public void givenMarkAndStudentId_notFindAnyStudent_thenThrowError(){
        Long studentId = 1L;
        Double mark = 5.0;

        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(StudentNotFoundException.class, () -> {
            studentService.addGradeToStudent(studentId, mark);
        });

        assertEquals("Student not found", exception.getMessage());
    }

    @Test
    public void givenStudentId_computeAverageGrade_thenReturnStudent(){
        Long studentId = 1L;
        Student student = Student.builder().id(studentId).name("Kamil").build();
        Grade grade1 = new Grade(student, 5.0);
        Grade grade2 = new Grade(student, 4.0);
        student.setGrades(List.of(grade1, grade2));

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        Student result = studentService.computeAverageGrade(studentId);

        assertNotNull(result);
        assertEquals(4.5, result.getAverageGrade());
    }

    @Test
    public void givenStudentId_notFindAnyGrades_theThrowError(){
        Long studentId = 1L;
        Student student = Student.builder().id(studentId).name("Kamil").build();

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        Exception exception = assertThrows(GradesNotFoundException.class, () -> {
            studentService.computeAverageGrade(studentId);
        });

        assertEquals("No grades found for the student", exception.getMessage());
    }

}
