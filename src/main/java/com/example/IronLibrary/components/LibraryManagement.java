package com.example.IronLibrary.components;

import com.example.IronLibrary.model.Author;
import com.example.IronLibrary.model.Book;
import com.example.IronLibrary.model.Student;
import com.example.IronLibrary.model.Issue;
import com.example.IronLibrary.repository.AuthorRepository;
import com.example.IronLibrary.repository.BookRepository;
import com.example.IronLibrary.repository.StudentRepository;
import com.example.IronLibrary.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Scanner;

@Component
public class LibraryManagement {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    IssueRepository issueRepository;
    Scanner scanner = new Scanner(System.in);

    public void displayMenu(){
        boolean repeat = true;
        String isbn;
        String bookTitle;
        String bookCategory;
        String authorName;
        String authorMail;
        int numbersOfBook;
        while(repeat){
            System.out.println( """
                                 Enter your choice:
                                 1- Add a book
                                 2- Search book by title
                                 3- Search book by category
                                 4- Search book by Author
                                 5- List all books along with author
                                 6- Issue book to student
                                 7- List books by usn
                                 8- Exit""");

            int choice = scanner.nextInt();
            switch (choice){
                case 1:
                    System.out.println("Enter isbn : ");
                    isbn = scanner.next();
                    System.out.println("Enter title : ");
                    bookTitle = scanner.next();
                    while (!bookTitle.matches("[A-Za-z\\s]+") ) {
                        System.out.println("Please enter valid title");
                        System.out.println("Enter title : ");
                        bookTitle = scanner.next();
                    }
                    System.out.println("Enter category :");
                    bookCategory = scanner.next();
                    while (!bookCategory.matches("[A-Za-z\\s]+")) {
                        System.out.println("Please enter valid category");
                        System.out.println("Enter category :");
                        bookCategory = scanner.next();
                    }
                    System.out.println("Enter Author name :");
                    authorName = scanner.next();
                    while (!authorName.matches("[A-Za-z\\s]+") || authorName.length() <= 3) {
                        System.out.println("Please enter valid name");
                        System.out.println("Enter Author name :");
                        authorName = scanner.next();
                    }
                    System.out.println("Enter Author mail :");
                    authorMail = scanner.next();
                    while (!authorMail.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")){
                        System.out.println("Please enter valid mail");
                        System.out.println("Enter Author mail :");
                        authorMail = scanner.next();
                    }
                    System.out.println("Enter number of books :");
                    numbersOfBook = scanner.nextInt();
                    while (numbersOfBook < 0){
                        System.out.println("Invalid number!!");
                        System.out.println("Enter number of books :");
                        numbersOfBook = scanner.nextInt();
                    }
                    addBook(isbn, bookTitle, bookCategory, numbersOfBook, authorName, authorMail);
                    break;
                case 2:
                    System.out.println("Enter title : ");
                    bookTitle = scanner.next();
                    searchBookByTitle(bookTitle);
                    break;
                case 3:
                    //Call method
                    break;
                case 4:
                    //Call method
                    break;
                case 5:
                    //Call method
                    break;
                case 6:
                    //Call method
                    break;
                case 7:
                    listBooksByUsn();
                    break;
                case 8:
                    //Call method
                    repeat = false;
                    System.exit(0);
                    break;
                default:
                    System.out.println("Unexpected value! Enter Number between 1-8: " + choice + "\n");
            }
        }
    }

    public void addBook(String isbn, String bookTitle, String bookCategory, int numberOfBooks, String authorName, String authorMail){
        isbn = "978-3-16-148410-0";
        bookTitle = "The Notebook";
        bookCategory = "Romance";
        numberOfBooks = 4;
        authorName = "Nicholas Sparks";
        authorMail = "nicholassparks@gmail.com";
        Book book = new Book(isbn, bookTitle, bookCategory, numberOfBooks);
        Author author = new Author(authorName, authorMail, book);

        bookRepository.save(book);
        authorRepository.save(author);

    }

    public void searchBookByTitle( String bookTitle){

        bookRepository.findByTitle(bookTitle);
    }

    public void listBooksByUsn() {
        System.out.println("Enter usn : ");
        String usn = scanner.next();
        // Find the student by USN
        Student student = studentRepository.findByUsn(usn);
        // Find the issued books for the student
        List<Issue> issuedBooks = issueRepository.findByIssueStudent(student);
        if (issuedBooks == null) {
            System.out.println("No books issued by student with usn: "+usn);
        }else{
        for ( Issue issuedBook : issuedBooks) {
            System.out.println("Book Title:"+issuedBook.getIssueBook().getTitle()+
                    "\nStudent Name:"+student.getName()+
                    "\nReturn date:"+issuedBook.getReturnDate());
        }
        }
    }

}
