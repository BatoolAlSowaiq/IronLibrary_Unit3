package com.example.IronLibrary.components;

import com.example.IronLibrary.model.Book;
import com.example.IronLibrary.model.Issue;
import com.example.IronLibrary.model.Student;
import com.example.IronLibrary.repository.BookRepository;
import com.example.IronLibrary.repository.IssueRepository;
import com.example.IronLibrary.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class LibraryManagementTest {

    @Autowired
    LibraryManagement libraryManagement;
    @Autowired
    IssueRepository issueRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    StudentRepository studentRepository;
    @Test
    void getValidName_validName_returnCorrectName() {

        // Simulate user input
        String name = "Test Input";
        //convert the input to byte stream
        InputStream inputStream = new ByteArrayInputStream(name.getBytes());
        //set the input stream to system.in
        System.setIn(inputStream);

        //check if the name is valid
        String result = libraryManagement.getValidName("Enter name : ");
        assertEquals("Test Input", result);
    }
    @Test
    void getValidName_inValidName_loopUntilCorrectName() {

        // First Iteration will get invalid String
        // will ask the user again,until get valid input
        String input = "AB\nA\nBatool AlSowaiq\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        String result = libraryManagement.getValidName("Enter name : ");
        assertEquals("Batool AlSowaiq", result);


    }

    @Test
    void getValidUsn_validUsn_returnCorrectUsn() {

        // Set up the test input
        String usn = "09003688800";
        //convert the input to byte stream
        InputStream inputStream = new ByteArrayInputStream(usn.getBytes());
        //set the input stream to system.in
        System.setIn(inputStream);

        // Call the method under test
        String result = libraryManagement.getValidUsn();

        // Assert the expected result
        assertEquals("09003688800", result);
    }
    @Test
    void getValidUsn_inValidUsn_loopUntilCorrectUsn() {

        // First Iteration will get invalid String
        // will ask the user again,until get valid input
        // Set up the test input
        String usn = "-12\nbn\n0008800\n09003688800\n";
        //convert the input to byte stream
        InputStream inputStream = new ByteArrayInputStream(usn.getBytes());
        //set the input stream to system.in
        System.setIn(inputStream);

        // Call the method under test
        String result = libraryManagement.getValidUsn();

        // Assert the expected result
        assertEquals("09003688800", result);
    }
    @Test
    void getValidIsbn_validIsbnWithSpace_returnCorrectIsbn() {

        // Set up the test input
        String isbn = "978 3161484100\n";
        //convert the input to byte stream
        InputStream inputStream = new ByteArrayInputStream(isbn.getBytes());
        //set the input stream to system.in
        System.setIn(inputStream);

        // Call the method under test
        String result = libraryManagement.getValidIsbn();

        // Assert the expected result
        assertEquals("978-3-16-148410-0", result);

    }
    @Test
    void getValidIsbn_validIsbnWithHyphens_returnCorrectIsbn() {

        // Set up the test input
        String isbn = "978-3-16-148410-0\n";
        //convert the input to byte stream
        InputStream inputStream = new ByteArrayInputStream(isbn.getBytes());
        //set the input stream to system.in
        System.setIn(inputStream);

        // Call the method under test
        String result = libraryManagement.getValidIsbn();

        // Assert the expected result
        assertEquals("978-3-16-148410-0", result);

    }
    @Test
    void getValidIsbn_validIsbnWithoutHyphensOrSpace_returnCorrectIsbn() {

        // Set up the test input
        String isbn = "9783161484100\n";
        //convert the input to byte stream
        InputStream inputStream = new ByteArrayInputStream(isbn.getBytes());
        //set the input stream to system.in
        System.setIn(inputStream);

        // Call the method under test
        String result = libraryManagement.getValidIsbn();

        // Assert the expected result
        assertEquals("978-3-16-148410-0", result);

    }
    @Test
    void getValidIsbn_inValidIsbn_loopUntilCorrectIsbn() {

        // First Iteration will get invalid String
        // will ask the user again,until get valid input

        // Set up the test input
        String isbn = "-1\nbhj\n978-3-16-1484\n978-3-16-148410-0\n";
        //convert the input to byte stream
        InputStream inputStream = new ByteArrayInputStream(isbn.getBytes());
        //set the input stream to system.in
        System.setIn(inputStream);

        // Call the method under test
        String result = libraryManagement.getValidIsbn();

        // Assert the expected result
        assertEquals("978-3-16-148410-0", result);

    }

    @Test
    void getValidEmail_validInput_returnCorrectEmail() {
        String input = "test@example.com\n"; // Provide a valid email address for testing (with a newline character)
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        String promptToAskUser = "Enter your email: ";

        String result = libraryManagement.getValidEmail(promptToAskUser);

        assertEquals("test@example.com", result);
    }
    @Test
    void getValidEmail_inValidInput_loopUntilCorrectEmail() {

        // Provide an invalid email address followed by a valid email address (with newline characters)
        String input = "invalidemail\nvalidemail@example.com\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);


        String result = libraryManagement.getValidEmail("Enter your email: ");
        // Assert that the returned email is equal to the expected valid email
        assertEquals("validemail@example.com", result);
    }


    @Test
    void handleBookIssue_bookAndStudent_bookIssued() {
        //create book and student
        Book book = new Book("978-3-16-148410-0","The Notebook","Romance",4);
        bookRepository.save(book);

        Student student = new Student("09003688800"," John Doe");

        //Issue the book to the student
        libraryManagement.handleBookIssue(book,student);

        //Check if the book is issued successfully
        Optional<Issue> issueOptional = issueRepository.findByIssueStudentAndIssueBook(student,book);

        assertTrue(issueOptional.isPresent());
        System.out.println("The issue: " + issueOptional.get());

        //check if the student is saved in the Student table
        Optional<Student> studentOptional =  studentRepository.findById("09003688800");
        assertTrue(studentOptional.isPresent());

        System.out.println("The student : " + studentOptional.get());

        //delete the records
        issueRepository.deleteById(issueOptional.get().getId());
        bookRepository.deleteById("978-3-16-148410-0");
        studentRepository.deleteById("09003688800");


    }

    @Test
    void handleBookIssue_bookAndStudent_bookNotIssued() {
        //create book
        Book book = new Book("978-3-16-148410-0","The Notebook","Romance",1);
        bookRepository.save(book);

        Student student1 = new Student("09003688800"," John Doe");
        Student student2 = new Student("01001111100","Batool Alsowaiq");

        //Issue the book to the student
        libraryManagement.handleBookIssue(book,student1);
        //Issue the book again to another student
        libraryManagement.handleBookIssue(book,student2);

        //Check if the book is not issued
        Optional<Issue> issueOptional1 = issueRepository.findByIssueStudentAndIssueBook(student1,book);

        //Check if the book is not issued
        Optional<Issue> issueOptional2 = issueRepository.findByIssueStudentAndIssueBook(student2,book);

        assertTrue(issueOptional2.isEmpty());
        System.out.println("No more book to issue!");

        //check if the student2 is not saved in the Student table
        Optional<Student> studentOptional =  studentRepository.findById("01001111100");
        assertTrue(studentOptional.isEmpty());
        System.out.println("Did not saved");

        //delete the records
        issueRepository.deleteById(issueOptional1.get().getId());
        bookRepository.deleteById("978-3-16-148410-0");
        studentRepository.deleteById("09003688800");


    }

    @Test
    public void getIssueDateAndReturnDate_correctStartDateAndReturnDate() {

        // Get the current date
        Date currentDate = new Date();

        // Create a calendar instance and add 7 days
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_YEAR, 7);

        // Get the expected return date
        Date expectedReturnDate = calendar.getTime();

        // Format the dates as strings
        SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
        String expectedIssueDateStr = formatter.format(currentDate);
        String expectedReturnDateStr = formatter.format(expectedReturnDate);

        // Call the method
        String[] dates = libraryManagement.getIssueDateAndReturnDate();

        // Assert that the Start & Returned dates match the expected dates
        assertEquals(expectedIssueDateStr, dates[0]);
        assertEquals(expectedReturnDateStr, dates[1]);
    }
}