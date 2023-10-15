package com.example.IronLibrary.components;

import com.example.IronLibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LibraryManagement {
    @Autowired
    BookRepository bookRepository;


    public void searchBookByTitle( String bookTitle){

        bookRepository.findByTitle(bookTitle);
    }

}
