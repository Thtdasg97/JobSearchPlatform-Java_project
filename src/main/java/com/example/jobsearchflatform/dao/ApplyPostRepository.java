package com.example.jobsearchflatform.dao;
import com.example.jobsearchflatform.entity.ApplyPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ApplyPostRepository extends JpaRepository<ApplyPost, Integer> {
    Page<ApplyPost> findByRecruitmentId(int recruitmentId, Pageable pageable);
}
