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
class BookRepositoryTest {

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
    }

    @AfterEach
    public void tearDown(){
        bookRepository.deleteById(book.getIsbn());
    }


    @Test
    public void addBook_newBook(){
        book = new Book("978-3-16-148410-0","The Notebook","Romance",4);
        author = new Author("Nicholas Sparks","nicholassparks@gmail.com", book);
        bookRepository.save(book);
        authorRepository.save(author);
    }

    @Test
    public void findByTitle_bookTitle_correctBook(){
        Optional<Book> bookOptional = bookRepository.findByTitle("The Notebook");
        assertTrue(bookOptional.isPresent());

        assertEquals("The Notebook",bookOptional.get().getTitle());
        System.out.println(bookOptional.get());
    }

    @Test
    public void findByCategory_category_correctBook(){

        book = new Book("978-3-16-148410-0","The Notebook","Romance",4);
        bookRepository.save(book);

        Optional<Book> bookOptional = bookRepository.findByCategory("Romance");
        assertTrue(bookOptional.isPresent());
        assertEquals("Romance",bookOptional.get().getCategory());
        bookRepository.deleteById("978-3-16-148410-0");

    }


}