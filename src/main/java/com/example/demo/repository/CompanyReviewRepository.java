package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.CompanyReview;

@Repository
public interface CompanyReviewRepository extends JpaRepository<CompanyReview, Long> {
    @Query(value = "select * from company_review c where c.company_id = :id", nativeQuery = true)
    List<CompanyReview> findByCompanyId(long id);

    @Query(value = "select * from company_review c where c.user_id = :id", nativeQuery = true)
    List<CompanyReview> findByUserId(long id);

}
