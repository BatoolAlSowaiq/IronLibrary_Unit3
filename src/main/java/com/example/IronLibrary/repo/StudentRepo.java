package com.example.IronLibrary.repo;

import com.example.IronLibrary.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo  extends JpaRepository<Student, String>  {

}
