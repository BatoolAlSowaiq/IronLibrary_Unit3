package com.example.IronLibrary.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String issueDate;
    private String returnDate;
    @OneToOne
    @JoinColumn(name = "student_id")
    private Student issueStudent;
    @OneToOne
    @JoinColumn(name = "isbn")
    private Book issueBook;

    public Issue(String issueDate, String returnDate, Student issueStudent, Book issueBook) {
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.issueStudent = issueStudent;
        this.issueBook = issueBook;
    }

}
