package com.example.IronLibrary.repository;

import com.example.IronLibrary.model.Author;
import com.example.IronLibrary.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Integer> {


}
