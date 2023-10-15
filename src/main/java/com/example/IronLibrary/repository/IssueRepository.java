package com.example.IronLibrary.repository;

import com.example.IronLibrary.model.Author;
import com.example.IronLibrary.model.Book;
import com.example.IronLibrary.model.Issue;
import com.example.IronLibrary.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Integer> {

   public Optional<Issue> findByBook (Book book);
   public Optional<Issue> findByStudentAndBook(Student student, Book book);
}
