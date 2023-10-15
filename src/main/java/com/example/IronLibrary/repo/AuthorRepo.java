package com.example.IronLibrary.repo;

import com.example.IronLibrary.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepo extends JpaRepository<Author, Integer> {
}
