package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    @Query(value = "SELECT * "
            + "FROM job WHERE company_id = ?1 ORDER BY ?#{#pageable}", nativeQuery = true)
    Page<Job> findByCompany(Long companyId, Pageable pageable);
}
