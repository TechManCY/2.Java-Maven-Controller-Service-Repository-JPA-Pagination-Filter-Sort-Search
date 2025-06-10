package com.example.paginationFilteringSorting_practice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "STUDENT")
@Data @AllArgsConstructor @NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private Double gpa;
    @ElementCollection
    @CollectionTable(
            name = "student_class_ids",
            joinColumns = @JoinColumn(name = "student_id")
    )
    @Column(name = "class_id")
    private List<Integer> classIds;

}
