package com.example.IronLibrary.repository;

import com.example.IronLibrary.model.Book;
import com.example.IronLibrary.model.Issue;
import com.example.IronLibrary.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class IssueRepositoryTest {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    IssueRepository issueRepository;
    @Test
    public void findByTitle_bookTitle_correctBook(){
        Book book = new Book("978-3-16-148410-0","The Notebook","Romance",4);
//        bookRepository.save(book);
        Student student = new Student("09003688800", "John Doe" );
//        studentRepository.save(student);
        Issue issue = new Issue("1","2",student,book);
//        issueRepository.save(issue);
        System.out.println(issue.toString()); // id = null?
        // Find the issued books for the student
        List<Issue> issuedBooks = issueRepository.findByIssueStudent(student);
        assertEquals(issue.getIssueDate(),issuedBooks.get(0).getIssueDate());
    }
}