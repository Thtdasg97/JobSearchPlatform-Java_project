package com.example.jobsearchflatform.dao;

import com.example.jobsearchflatform.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    Company findCompanyByUserId(int theUserId);
    Company findCompanyByEmail(String theEmail);

}
