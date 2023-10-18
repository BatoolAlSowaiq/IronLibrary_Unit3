package com.example.IronLibrary.repository;

import com.example.IronLibrary.model.Author;
import com.example.IronLibrary.model.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AuthorRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;
    private Book book;
    private Author author;
@BeforeEach
    public void setUp(){
    book = new Book("978-3-16-148410-0","The Notebook","Romance",4);
    bookRepository.save(book);
        author = new Author("Nicholas Sparks", "nicholassparks@gmail.com", book);
        authorRepository.save(author);

    }

    @AfterEach
    public void tearDown(){
        authorRepository.deleteById(author.getAuthorId());
        bookRepository.deleteById(book.getIsbn());
    }


    @Test
    public void findByAuthor_Author_correctBook(){

        Optional<Author> autherOptional = authorRepository.findByName("Nicholas Sparks");
        assertTrue(autherOptional.isPresent());
        System.out.println(autherOptional);
        assertEquals("Nicholas Sparks",autherOptional.get().getName());
    }

    @Test
    public void findByAuthor_Author_NotFound(){
        Optional<Author> autherOptional = authorRepository.findByName("Hassan");
        assertTrue(autherOptional.isEmpty());
        System.out.println("Not FOUND!!!");
    }

    @Test
    public void testListAllBooksWithAuthors() {

        // Call the listAllBooksWithAuthors() method
        List<Author> authors = authorRepository.findAll();

        // Check that the list contains the saved book with author
        assertEquals(1,authors.size());

    }

}