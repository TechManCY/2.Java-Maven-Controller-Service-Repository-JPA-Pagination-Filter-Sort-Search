package com.example.paginationFilteringSorting_practice.controller;

import com.example.paginationFilteringSorting_practice.model.Student;
import com.example.paginationFilteringSorting_practice.model.StudentPage;
import com.example.paginationFilteringSorting_practice.model.StudentSearchCriteria;
import com.example.paginationFilteringSorting_practice.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<Page<Student>> getStudents(StudentPage studentPage){
        return new ResponseEntity<>(studentService.getStudent(studentPage), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Student>> getStudentsByCriteria(StudentPage studentPage,
                                                               StudentSearchCriteria studentSearchCriteria){
        return new ResponseEntity<>(studentService.getStudentByCriteria(studentPage , studentSearchCriteria), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student){
        return new ResponseEntity<>(studentService.addStudent(student), HttpStatus.CREATED);
    }

}
