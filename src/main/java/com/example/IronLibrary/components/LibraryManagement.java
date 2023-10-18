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

import java.text.SimpleDateFormat;
import java.util.*;

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

    public String getValidName(String promptToAskUser) {

        Scanner scanner = new Scanner(System.in);
        String name;
        while (true) {
            //take user input
            System.out.print(promptToAskUser);
            name = scanner.nextLine();

            //if user input is empty or too short
            if (name.length() >= 3 && name.matches("[A-Za-z\\s]+")) break;
            else {
                System.out.println("Invalid Input! Name must be at least 3 character");
            }

        }
        return name;
    }

    public String getValidUsn() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Please enter the USN: ");
            String usn = scanner.nextLine();

            // Validate USN format
            if (usn.matches("\\d{11}")) {
                return usn;
            } else {
                System.out.println("Invalid USN. Please try again.");
            }
        }
    }

    public String getValidIsbn() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter the book ISBN: ");
            String isbn = scanner.nextLine();

            // Remove hyphens and spaces from the entered ISBN
            String sanitizedISBN = isbn.replaceAll("[\\s-]", "");

            // Validate ISBN format
            if (sanitizedISBN.matches("\\d{13}")) {
                // Format the ISBN as 978-3-16-148410-0
                StringBuilder formattedISBN = new StringBuilder(sanitizedISBN);
                formattedISBN.insert(3, "-");
                formattedISBN.insert(5, "-");
                formattedISBN.insert(8, "-");
                formattedISBN.insert(15, "-");
                return formattedISBN.toString();
            } else {
                System.out.println("Invalid ISBN");
            }
        }
    }

    public String getValidEmail(String promptToAskUser) {
        Scanner scanner = new Scanner(System.in);
        String inputEmail;
        while (true) {

            //get email from user
            System.out.print(promptToAskUser);
            inputEmail = scanner.nextLine().trim();

            if (inputEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) break;
            else {
                System.out.println("Invalid email address. Please enter a valid email.");
            }


        }
        return inputEmail;
    }

    public static Integer getValidIntegerInput(String promptToAskUser){

        Scanner scanner = new Scanner(System.in);
        int integerInput;
        while (true){
            try{
                //take user input
                System.out.print(promptToAskUser);
                integerInput = scanner.nextInt();

                //if user input is negative
                if(integerInput >= 0) break;
                else {System.out.println("Invalid input. You must enter positive integer number");}

                //if user input is non-integer, handle the exception
            }catch (InputMismatchException e){

                System.out.println("Invalid input. You must enter positive integer number");
                //delete the invalid input from scanner
                scanner.next();
            }
        }return integerInput;
    }

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean repeat = true;

        while (repeat) {
            System.out.println("""
                    -----------------------------------------------------------------
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
            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    searchBookByTitle();
                    break;
                case 3:
                    searchBookByCategory();
                    break;
                case 4:
                    searchBookByAuthor();
                    break;
                case 5:
                    listAllBooksWithAuthors();
                    break;
                case 6:
                    issueBookToStudent();
                    break;
                case 7:
                    listBooksByUsn();
                    break;
                case 8:
                    System.out.println("thank you for using Iron Library");
                    repeat = false;
                    System.exit(0);
                    break;
                default:
                    System.out.println("Unexpected value! Enter Number between 1-8: " + choice + "\n");
            }
        }
    }

    public void addBook() {
        String isbn = getValidIsbn();
        String bookTitle = getValidName("Enter title : ");
        String bookCategory = getValidName("Enter category : ");
        Integer numberOfBooks = getValidIntegerInput("Enter number of books :");
        String authorName = getValidName("Enter author name : ");
        String authorMail = getValidEmail("Enter author mail : ");

        Book book = new Book(isbn, bookTitle, bookCategory, numberOfBooks);
        Author author = new Author(authorName, authorMail, book);

        bookRepository.save(book);
        authorRepository.save(author);

        System.out.println("The book is added successfully. ");

    }

    public void searchBookByTitle() {
        String title = getValidName("Enter title : ");
        Optional<Book> bookOptional = bookRepository.findByTitle(title);
        if (bookOptional.isPresent()) {
            System.out.println("\nISBN          Title           Category            num. of books\n");
            System.out.println(bookOptional.get());
        } else {
            System.out.println("The book is not found. ");
        }
    }

    public void searchBookByCategory() {
        String category = getValidName("Enter category : ");
        Optional<Book> bookOptional = bookRepository.findByCategory(category);
        if (bookOptional.isPresent()) {
            System.out.println("\nISBN          Title           Category            num. of books");
            System.out.println(bookOptional.get());
        } else {
            System.out.println("There is no book belongs to this" + category);
        }
    }

    public void searchBookByAuthor() {
        String authorName = getValidName("Enter Author name :");

        Optional<Author> authorOptional = authorRepository.findByName(authorName);
        if (authorOptional.isPresent()) {
            System.out.println("\nISBN          Title           Category            num. of books       author name         email:\n");
            System.out.println(authorOptional.get());
        } else {
            System.out.println("There is no book corresponding to this " + authorName);
        }
    }

    public void listAllBooksWithAuthors() {
        // Fetch all books from the repository
        List<Author> authors = authorRepository.findAll();
        if (authors.isEmpty()) {
            System.out.println("No books found.");
        } else {
            System.out.println("ISBN            Title           Category            num. of books       author name         email:\n");
            // Loop through each book and print its details along with the author's information
            for (Author author : authors) {
                System.out.println(author);
            }
        }
    }

    public void issueBookToStudent() {

        // Get student info
        Student currentStudent = getStudent();

        // Get book ISBN
        String isbn = getValidIsbn();

        // Search about the book by its ISBN
        Optional<Book> bookOptional = bookRepository.findById(isbn);


        if (bookOptional.isPresent()) {
            //If the book is found, handle the issue cases
            handleBookIssue(bookOptional.get(), currentStudent);
        } else {
            System.out.println("Sorry, the book is not found!");
        }

    }

    public Student getStudent() {
        String usn = getValidUsn();
        String name = getValidName("Enter student name : ");

        return new Student(usn, name);
    }

    public void handleBookIssue(Book book, Student student) {

        //Search if the book  is issued to current student
        Optional<Issue> currentStudentIssue = issueRepository.findByIssueStudentAndIssueBook(student, book);

        // Search if there is no more book to issue (book quantity = 0)
        List<Issue> issueList = issueRepository.findByIssueBook(book);

        if (currentStudentIssue.isPresent()) {
            System.out.println("You already issued this book");
        } else if (issueList.size() == book.getQuantity()) {
            System.out.println("Sorry, there is no more book to issue");
        } else {
            //Get the issueDate and returnDate in the proper format.
            String[] date = getIssueDateAndReturnDate();

            //Issue the book to the student
            Issue issue = new Issue(date[0], date[1], student, book);
            System.out.println("Book issued. Return date: " + date[1]);

            //Save the student in the student table
            studentRepository.save(student);
            //Save the issue in its table
            issueRepository.save(issue);
        }

    }

    public String[] getIssueDateAndReturnDate() {

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


    public void listBooksByUsn() {
        String usn = getValidUsn();
        Student student = studentRepository.findByUsn(usn);
        // Find the issued books for the student
        List<Issue> issuedBooks = issueRepository.findByIssueStudent(student);
        if (issuedBooks == null) {
            System.out.println("No books issued by student with usn: " + usn);
        } else {
            for (Issue issuedBook : issuedBooks) {
                System.out.println("Book Title:" + issuedBook.getIssueBook().getTitle() +
                        "\nStudent Name:" + student.getName() +
                        "\nReturn date:" + issuedBook.getReturnDate());
            }
        }
    }
}