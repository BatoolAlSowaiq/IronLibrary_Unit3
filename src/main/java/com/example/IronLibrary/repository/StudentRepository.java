package com.example.IronLibrary.repository;

import com.example.IronLibrary.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, String>  {
    Student findByUsn(String usn);
}
