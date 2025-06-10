package com.example.paginationFilteringSorting_practice.repository;

import com.example.paginationFilteringSorting_practice.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository <Student, Long> , PagingAndSortingRepository <Student, Long> {
    //filter by one item from an attribute of a list
    @Query("SELECT s FROM Student s JOIN s.classIds c WHERE c = :classId")
    Page<Student> findByClassId(@Param("classId") Integer classId, Pageable pageable);
}
