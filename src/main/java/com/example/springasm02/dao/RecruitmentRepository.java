package com.example.springasm02.dao;
import com.example.springasm02.entity.Recruitment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Integer> {
    List<Recruitment> findRecruitmentByCompanyId(int companyId);
    List<Recruitment> findRecruitmentByCategoryId(int categoryId);
    List<Recruitment> findTop3ByOrderBySalaryDesc();
    long count();
    Page<Recruitment> findByCompanyId(int companyId, Pageable pageable);
    List<Recruitment> findByTitleContainingIgnoreCase(String keySearch);
    List<Recruitment> findByCompanyCompanyNameContainingIgnoreCase(String keySearch);
    List<Recruitment> findByAddressContainingIgnoreCase(String keySearch);
}
