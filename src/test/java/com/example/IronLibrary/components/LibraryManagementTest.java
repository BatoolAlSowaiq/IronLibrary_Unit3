package com.example.IronLibrary.components;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class LibraryManagementTest {

    @Autowired
    LibraryManagement libraryManagement;
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
}