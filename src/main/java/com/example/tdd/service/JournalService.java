package com.example.tdd.service;

import com.example.tdd.dto.JournalCreateDto;
import com.example.tdd.model.Journal;
import com.example.tdd.model.Student;
import com.example.tdd.repository.JournalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class JournalService {

    private JournalRepository journalRepository;

    public Journal createJournal(JournalCreateDto journalDto){
        var journal = Journal.builder().name(journalDto.name()).build();
        return journalRepository.save(journal);
    }

    public List<Student> getStudents(Long journalId){
        return new ArrayList<>();
    }

}
