package com.example.IronLibrary.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    private String isbn;
    @Getter
    private String title;
    private String category;
    private Integer quantity;

    @Override
    public String toString() {
        return isbn+"         "+title + "       " +category +"         "+quantity;
    }
}
