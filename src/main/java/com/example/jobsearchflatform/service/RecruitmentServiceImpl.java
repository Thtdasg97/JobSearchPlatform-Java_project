package com.example.jobsearchflatform.service;

import com.example.jobsearchflatform.dao.RecruitmentRepository;
import com.example.jobsearchflatform.entity.Recruitment;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecruitmentServiceImpl implements RecruitmentService{
    private RecruitmentRepository recruitmentRepository;
@Autowired
    public RecruitmentServiceImpl(RecruitmentRepository theRecruitmentRepository) {
        recruitmentRepository = theRecruitmentRepository;
    }
    @Override
    public List<Recruitment> findAllRecruitment() {
        return recruitmentRepository.findAll();
    }

    @Override
    public Recruitment findRecruitmentById(int theId) {
        Optional<Recruitment> result = recruitmentRepository.findById(theId);

        Recruitment theRecruitment = null;

        if(result.isPresent()) {
            theRecruitment = result.get();
        } else {
            throw new RuntimeException("Did not find recruitment id - " + theId);
        }
        return theRecruitment;
    }

    @Override
    public List<Recruitment> findRecruitmentByCompanyId(int companyId) {
        return recruitmentRepository.findRecruitmentByCompanyId(companyId);
    }

    @Override
    public List<Recruitment> findRecruitmentByCategoryId(int categoryId) {
        return recruitmentRepository.findRecruitmentByCategoryId(categoryId);
    }

    @Override
    public Page<Recruitment> getAllRecruitments(int pageNumber, int pageSize, int companyId) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return recruitmentRepository.findByCompanyId(companyId,pageable);
    }

    @Override
    public int getTotalPages(int pageSize) {
        long totalRecruitments = recruitmentRepository.count();
        return (int) Math.ceil((double) totalRecruitments/ pageSize);
    }

    @Override
    public void saveRecruitment(Recruitment theRecruitment) {
        recruitmentRepository.save(theRecruitment);
    }

    @Override
    public Recruitment updatedRecruitment(Recruitment theRecruitment) {
    Optional<Recruitment> result = recruitmentRepository.findById(theRecruitment.getId());

    if(result.isPresent()) {
        Recruitment existingRecruitment = result.get();

        existingRecruitment.setTitle(theRecruitment.getTitle());
        existingRecruitment.setDescription(theRecruitment.getDescription());
        existingRecruitment.setExperience(theRecruitment.getExperience());
        existingRecruitment.setQuantity(theRecruitment.getQuantity());
        existingRecruitment.setAddress(theRecruitment.getAddress());
        existingRecruitment.setDeadline(theRecruitment.getDeadline());
        existingRecruitment.setSalary(theRecruitment.getSalary());
        existingRecruitment.setType(theRecruitment.getType());
        existingRecruitment.setCategory(theRecruitment.getCategory());

        return recruitmentRepository.save(existingRecruitment);
    } else {
        throw new EntityNotFoundException("Recruitment with Id " + theRecruitment.getAddress() + " not found");
    }

    }
    @Override
    public void deleteRecruitmentById(int recruitmentId) {
        recruitmentRepository.deleteById(recruitmentId);
    }
    @Override
    public List<Recruitment> searchRecruitmentByKey(String keySearch) {
        return recruitmentRepository.findByTitleContainingIgnoreCase(keySearch);
    }
    @Override
    public List<Recruitment> searchRecruitmentByCompanyName(String keySearch) {
        return recruitmentRepository.findByCompanyCompanyNameContainingIgnoreCase(keySearch);
    }

    @Override
    public List<Recruitment> searchRecruitmentByAddress(String keySearch) {
        return recruitmentRepository.findByAddressContainingIgnoreCase(keySearch);
    }

    @Override
    public List<Recruitment> getTop3HighestSalaryRecruitments() {
        return recruitmentRepository.findTop3ByOrderBySalaryDesc();
    }


}
