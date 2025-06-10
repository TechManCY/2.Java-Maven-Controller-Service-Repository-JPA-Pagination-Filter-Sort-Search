package com.example.paginationFilteringSorting_practice.service;

import com.example.paginationFilteringSorting_practice.model.Student;
import com.example.paginationFilteringSorting_practice.model.StudentPage;
import com.example.paginationFilteringSorting_practice.model.StudentSearchCriteria;
import com.example.paginationFilteringSorting_practice.repository.StudentCriteriaRepository;
import com.example.paginationFilteringSorting_practice.repository.StudentRepository;
import org.springframework.data.domain.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentCriteriaRepository studentCriteriaRepository;

    // filter by one item from an attribute of a list
    public Page<Student> searchByClassId(Integer classId, StudentPage studentPage){
        Sort sort = Sort.by(studentPage.getSortDirection(), studentPage.getSortBy());
        Pageable pageable = PageRequest.of(
                studentPage.getPageNumber(),
                studentPage.getPageSize(),
                sort );
        return studentRepository.findByClassId(classId, pageable);
    }

    //filter by list from an attribute of a list - - OR search, not an AND search
    public Page<Student> searchByClassIds(List<Integer> classIds, StudentPage studentPage) {
        Sort sort = Sort.by(studentPage.getSortDirection(), studentPage.getSortBy());
        Pageable pageable = PageRequest.of(
                studentPage.getPageNumber(),
                studentPage.getPageSize(),
                sort
        );
        return studentRepository.findByClassIds(classIds, pageable);
    }


    // filter by Criteria
    public Page<Student> getStudentByCriteria(StudentPage studentPage,
                                              StudentSearchCriteria studentSearchCriteria) {
        return studentCriteriaRepository.findAllWithFilters(studentPage, studentSearchCriteria);
    }

    //get Page
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
