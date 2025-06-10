package com.example.paginationFilteringSorting_practice.repository;

import com.example.paginationFilteringSorting_practice.model.Student;
import com.example.paginationFilteringSorting_practice.model.StudentPage;
import com.example.paginationFilteringSorting_practice.model.StudentSearchCriteria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class StudentCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public StudentCriteriaRepository (EntityManager entityManager){
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Student> findAllWithFilters (StudentPage studentPage,
                                             StudentSearchCriteria studentSearchCriteria){
        CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class);
        Root<Student> studentRoot = criteriaQuery.from(Student.class);
        Predicate predicate = getPredicate(studentSearchCriteria, studentRoot);
        criteriaQuery.where(predicate);
        setOrder(studentPage,criteriaQuery,studentRoot);

        TypedQuery<Student> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(studentPage.getPageNumber()*studentPage.getPageSize());
        typedQuery.setMaxResults(studentPage.getPageSize());

        Pageable pageable = getPageable(studentPage);

        long studentCount = getStudentCount(studentSearchCriteria);

        return new PageImpl<>(typedQuery.getResultList(),pageable, studentCount);
    }

    private long getStudentCount(StudentSearchCriteria studentSearchCriteria) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Student> countRoot = countQuery.from(Student.class);

        Predicate predicate = getPredicate(studentSearchCriteria, countRoot);

        countQuery.select(criteriaBuilder.count(countRoot));
        if (predicate != null) {
            countQuery.where(predicate);
        }

        return entityManager.createQuery(countQuery).getSingleResult();
    }

    private Pageable getPageable(StudentPage studentPage) {
        Sort sort = Sort.by(studentPage.getSortDirection(), studentPage.getSortBy());
        return PageRequest.of(studentPage.getPageNumber(), studentPage.getPageSize(), sort);
    }

    private void setOrder(StudentPage studentPage,
                          CriteriaQuery<Student> criteriaQuery,
                          Root<Student> studentRoot) {
        if (studentPage.getSortDirection().equals(Sort.Direction.ASC)){
            criteriaQuery.orderBy(criteriaBuilder.asc(studentRoot.get(studentPage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(studentRoot.get(studentPage.getSortBy())));
        }
    }


    private Predicate getPredicate(StudentSearchCriteria studentSearchCriteria,
                                   Root<Student> studentRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(studentSearchCriteria.getFirstName())){
            predicates.add(
                    criteriaBuilder.like(studentRoot.get("firstName"),"%"+studentSearchCriteria.getFirstName() + "%")
            );
        }
        if (Objects.nonNull(studentSearchCriteria.getLastName())){
            predicates.add(
                    criteriaBuilder.like(studentRoot.get("lastName"),"%"+studentSearchCriteria.getLastName() + "%")
            );
        }
        // range search
        if (Objects.nonNull(studentSearchCriteria.getMinGrade())){
            predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(studentRoot.get("minGrade"),studentSearchCriteria.getMinGrade())
            );
        }
        if (Objects.nonNull(studentSearchCriteria.getMaxGrade())){
            predicates.add(
                    criteriaBuilder.lessThanOrEqualTo(studentRoot.get("maxGrade"),studentSearchCriteria.getMaxGrade())
            );
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }
    }


