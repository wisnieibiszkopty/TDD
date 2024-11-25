package com.example.tdd.service;

import com.example.tdd.dto.StudentDto;
import com.example.tdd.model.Journal;
import com.example.tdd.model.Student;
import com.example.tdd.repository.GradeRepository;
import com.example.tdd.repository.JournalRepository;
import com.example.tdd.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final JournalRepository journalRepository;
    private final GradeRepository gradeRepository;

    public Student createStudent(StudentDto studentDto){
        return null;
    }

    public Journal addToJournal(Long studentId, Long journalId){
        return null;
    }

    public Student addGradeToStudent(Long studentId, Double mark){
        return null;
    }

    public Double computeAverageGrade(Long studentId){
        return 0.0;
    }

}
