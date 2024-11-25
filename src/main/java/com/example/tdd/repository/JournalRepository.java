package com.example.tdd.repository;

import com.example.tdd.model.Journal;
import com.example.tdd.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JournalRepository extends JpaRepository<Journal, Long> {

}
