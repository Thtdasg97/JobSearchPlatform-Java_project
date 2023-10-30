package com.example.springasm02.service;


import com.example.springasm02.entity.Company;
import com.example.springasm02.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CompanyService {
    List<Company> findAllCompanies();
    Company findCompanyById(int theId);
    Company findCompanyByUserId(int theUserId);
    Company updateCompany(Company theCompany);
    Company findCompanyByEmail(String theEmail);
    void saveCompany(Company theCompany);
    void saveFile(MultipartFile file, String logoPath) throws IOException;
//    List<Company> getTop3CompaniesByRecruitmentCount();
}
