package com.example.IronLibrary.components;


import com.example.IronLibrary.model.Author;
import com.example.IronLibrary.repository.*;
import com.example.IronLibrary.model.Author;
import com.example.IronLibrary.model.Book;
import com.example.IronLibrary.model.Issue;
import com.example.IronLibrary.model.Student;
import com.example.IronLibrary.model.Author;
import com.example.IronLibrary.repository.AuthorRepository;
import jdk.jfr.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Component
public class LibraryManagement {
    @Autowired
     BookRepository bookRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    IssueRepository issueRepository;
    @Autowired
     AuthorRepository authorRepository;

    public String getValidName(String promptToAskUser){

        Scanner scanner = new Scanner(System.in);
        String name;
        while (true){
            //take user input
            System.out.print(promptToAskUser);
            name = scanner.nextLine();

            //if user input is empty or too short
            if( name.length() >= 3 ) break;
            else {System.out.println("Invalid Input! Name must be at least 3 character");}

        }return name;
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
    public String getValidEmail(String promptToAskUser){
        Scanner scanner = new Scanner(System.in);
        String inputEmail;
        while (true) {

            //get email from user
            System.out.print(promptToAskUser);
            inputEmail = scanner.nextLine().trim();

            if (inputEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) break;
            else {System.out.println("Invalid email address. Please enter a valid email.");}


        }
        return inputEmail;
    }
    public void displayMenu(){
        Scanner scanner = new Scanner(System.in);
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
                    System.out.println("Enter category : ");
                    bookCategory = scanner.next();
                    searchBookByCategory(bookCategory);
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
                    //Call method
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


    public void searchBookByTitle(String bookTitle){
        bookRepository.findByTitle(bookTitle);
    }
    public Student getStudent() {

        String usn = getValidUsn();
        String name = getValidName("Enter student name : ");

        return new Student(usn,name);
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

            //Save the student in the student table ????????????????????
            studentRepository.save(student);
            //Save the issue in its table
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





    public void searchBookByCategory( String category){
        bookRepository.findByCategory(category);
    }


    public void searchBookByAuthor(String authorName){

        authorRepository.findByName(authorName);
    }
    public void listAllBooksWithAuthors() {
        // Fetch all books from the repository
        List<Book> books = bookRepository.findAll();
        if (books.isEmpty()) {
            System.out.println("No books found.");
        } else {
            // Loop through each book and print its details along with the author's information
            for (Book book : books) {
                Author author = book.getAuthor();
                if (author != null) {
                    System.out.println("Book ISBN: " + book.getIsbn());
                    System.out.println("Book Title: " + book.getTitle());
                    System.out.println("Book Category: " + book.getCategory());
                    System.out.println("No of Books: " + book.getQuantity());
                    System.out.println("Author Name: " + author.getName());
                    System.out.println("Author Mail: " + author.getEmail());
                    System.out.println();
                }
            }
        }

    }}
