package com.example.IronLibrary.repo;

import com.example.IronLibrary.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IIssueRepo extends JpaRepository<Issue, Integer> {

}
