package com.example.IronLibrary.repository;

import com.example.IronLibrary.model.Author;
import com.example.IronLibrary.model.Book;
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
    @Test
    public void testListAllBooksWithAuthors() {
        // Create and save an Author
        Author author = new Author("Nicholas Sparks", "nicholassparks@gmail.com", null);
        authorRepository.save(author);

        // Create and save a Book with the Author relationship
        Book book = new Book("978-3-16-148410-0", "The Notebook", "Romance", 4);
        book.setAuthor(author); // Set the Author for the Book
        bookRepository.save(book);

        // Call the listAllBooksWithAuthors() method
        List<Book> books = bookRepository.findAll();

        // Check that the list contains the saved book with author
        assertEquals(1, books.size());

        // Verify the details of the book and author
        Book retrievedBook = books.get(0);
        Author retrievedAuthor = retrievedBook.getAuthor();
        assertEquals("978-3-16-148410-0", retrievedBook.getIsbn());
        assertEquals("The Notebook", retrievedBook.getTitle());
        assertEquals("Romance", retrievedBook.getCategory());
        assertEquals(4, retrievedBook.getQuantity());
        assertEquals("Nicholas Sparks", retrievedAuthor.getName());
        assertEquals("nicholassparks@gmail.com", retrievedAuthor.getEmail());
    }

}