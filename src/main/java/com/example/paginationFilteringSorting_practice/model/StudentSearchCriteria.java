package com.example.paginationFilteringSorting_practice.model;

import lombok.Data;

import java.util.List;

@Data
public class StudentSearchCriteria {
    private String firstName;
    private String lastName;
    private Double minGpa;
    private Double maxGpa;
    private Integer classId; // filter for a specific class.
    private List<Integer> classIds; //filter by multiple class IDs
}
