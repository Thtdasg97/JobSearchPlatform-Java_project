package com.example.jobsearchflatform.service;

import com.example.jobsearchflatform.entity.Recruitment;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RecruitmentService {

    List<Recruitment> findAllRecruitment();
    Recruitment findRecruitmentById(int theId);
    List<Recruitment> findRecruitmentByCompanyId(int companyId);
    List<Recruitment> findRecruitmentByCategoryId(int categoryId);
    Page<Recruitment> getAllRecruitments(int pageNumber, int pageSize, int companyId);
    int getTotalPages(int pageSize);
    void saveRecruitment(Recruitment theRecruitment);
    Recruitment updatedRecruitment(Recruitment theRecruitment);
    void deleteRecruitmentById(int recruitmentId);
    List<Recruitment> searchRecruitmentByKey(String keySearch);
    List<Recruitment> searchRecruitmentByCompanyName(String keySearch);
    List<Recruitment> searchRecruitmentByAddress(String keySearch);
    List<Recruitment> getTop3HighestSalaryRecruitments();

}
