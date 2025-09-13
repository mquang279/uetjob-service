package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
        @Query(value = "SELECT * "
                        + "FROM job WHERE company_id = ?1 ORDER BY created_at DESC", nativeQuery = true)
        Page<Job> findByCompany(Long companyId, Pageable pageable);

        @Query(value = "SELECT * "
                        + "FROM job WHERE active = true ORDER BY created_at DESC", nativeQuery = true)
        Page<Job> findActiveJobs(Pageable pageable);

        @Query(value = "SELECT DISTINCT j.* "
                        + "FROM job j "
                        + "LEFT JOIN job_skill js ON j.id = js.job_id "
                        + "LEFT JOIN skill s ON s.id = js.skill_id "
                        + "LEFT JOIN companies c ON j.company_id = c.id "
                        + "WHERE j.active = true "
                        + "AND (j.description LIKE CONCAT('%', :param, '%') "
                        + "OR j.title LIKE CONCAT('%', :param, '%') "
                        + "OR c.name LIKE CONCAT('%', :param, '%') "
                        + "OR s.name LIKE CONCAT('%', :param, '%')) "
                        + "ORDER BY j.created_at DESC", nativeQuery = true)
        Page<Job> findJobByParam(Pageable pageable, String param);

        @Query(value = "SELECT COUNT(*) FROM job WHERE active = true", nativeQuery = true)
        Long countActiveJobs();
}
