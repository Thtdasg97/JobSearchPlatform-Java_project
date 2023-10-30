package com.example.springasm02.dao;

import com.example.springasm02.entity.Company;
import com.example.springasm02.entity.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    Company findCompanyByUserId(int theUserId);
    Company findCompanyByEmail(String theEmail);

}
