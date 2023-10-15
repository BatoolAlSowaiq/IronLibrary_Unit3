package com.example.IronLibrary.components;

import com.example.IronLibrary.model.Book;
import com.example.IronLibrary.model.Issue;
import com.example.IronLibrary.model.Student;
import com.example.IronLibrary.repository.BookRepository;
import com.example.IronLibrary.repository.IssueRepository;
import com.example.IronLibrary.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.Scanner;

@Component
public class LibraryManagement {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private IssueRepository issueRepository;



    public void searchBookByTitle(String bookTitle){

        bookRepository.findByTitle(bookTitle);
    }
    public void issueBookToStudent() {

        Scanner scanner = new Scanner(System.in);

        // Get student info
        Student currentStudent = getStudentInfo(scanner);

        // Get book ISBN
        String isbn = getValidBookISBN(scanner);

        // Search about the book by its ISBN
        Optional<Book> bookOptional = bookRepository.findById(isbn);

        if (bookOptional.isPresent()) {
            //If the book is found, handle the issue cases
            handleBookIssue(bookOptional.get(), currentStudent);
        } else {
            System.out.println("Sorry, the book is not found!");
        }

        scanner.close();
    }
    public void  getStudentInfo(Scanner scanner) {
        //return student
    }
    public void  getSValidName(Scanner scanner) {
        //return valid name (book  or student)
    }
    public void getValidStudentUSN(){
        //return valid usn
    }
    public void getValidBookISBN(){
        //return valid isbn
    }

    public void handleBookIssue(Book book, Student student) {

        //Search if the book  is issued to current student
        Optional<Issue> currentStudentIssue = issueRepository.findByStudentAndBook(student, book);

        // Search if the book is issued to another student
        Optional<Issue> anotherStudentIssue = issueRepository.findByBook(book);

        if (currentStudentIssue.isPresent()) {
            System.out.println("The book is already issued to " + student.getName());
        } else if (anotherStudentIssue.isPresent()) {
            System.out.println("The book is already issued to another student.");
        } else {

            //Get the issueDate and returnDate in the proper format.
            String[] date = getIssueDateAndReturnDate();

            //Issue the book to the student
            Issue issue = new Issue(date[0], date[1], student, book);
            System.out.println("Book issued. Return date: " + date[1]);

            //Save the record
            issueRepository.save(issue);
        }

    }

    public String[] getIssueDateAndReturnDate(){

            // Get the current date
            Date currentDate = new Date();

            // Create a calendar instance and add 7 days
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.add(Calendar.DAY_OF_YEAR, 7);

            // Get the return date
            Date returnDate = calendar.getTime();

            // Format the dates as strings
            SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
            String issueDateStr = formatter.format(currentDate);
            String returnDateStr = formatter.format(returnDate);

            // Create and return an array of dates
            String[] dates = new String[2];
            dates[0] = issueDateStr;
            dates[1] = returnDateStr;
            return dates;
    }




}
