package com.example.paginationFilteringSorting_practice.service;

import com.example.paginationFilteringSorting_practice.model.Student;
import com.example.paginationFilteringSorting_practice.model.StudentPage;
import com.example.paginationFilteringSorting_practice.repository.StudentRepository;
import org.springframework.data.domain.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public Page<Student> getStudent(StudentPage studentPage){
        Sort sort = Sort.by(studentPage.getSortDirection(), studentPage.getSortBy());
        Pageable pageable = PageRequest.of(
                studentPage.getPageNumber(),
                studentPage.getPageSize(),
                sort );
        return studentRepository.findAll(pageable);
    }

    public Student addStudent(Student student){
        return studentRepository.save(student);
    }


}
