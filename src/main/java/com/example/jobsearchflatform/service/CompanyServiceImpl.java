package com.example.jobsearchflatform.service;

import com.example.jobsearchflatform.dao.CompanyRepository;
import com.example.jobsearchflatform.entity.Company;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {
    private CompanyRepository companyRepository;
    @Autowired
    public CompanyServiceImpl(CompanyRepository theCompanyRepository) {
        companyRepository = theCompanyRepository;
    }
    @Override
    public List<Company> findAllCompanies() {
        List<Company> companies = companyRepository.findAll();
        return companies;
    }
    @Override
    public Company findCompanyById(int theId) {
        Optional<Company> result = companyRepository.findById(theId);

        Company theCompany = null;

        if(result.isPresent()) {
            theCompany = result.get();
        } else {
            throw new RuntimeException("Did not find company id - " + theId);
        }
        return theCompany;
    }
    @Override
    public Company findCompanyByUserId(int theUserId) {
        return companyRepository.findCompanyByUserId(theUserId);
    }
    @Override
    public Company updateCompany(Company theCompany) {
        Optional<Company> optionalExistingCompany = companyRepository.findById(theCompany.getId());

        if(optionalExistingCompany.isPresent()) {
            Company existingCompany = optionalExistingCompany.get();

            existingCompany.setEmail(theCompany.getEmail());
            existingCompany.setCompanyName(theCompany.getCompanyName());
            existingCompany.setAddress(theCompany.getAddress());
            existingCompany.setPhoneNumber(theCompany.getPhoneNumber());
            existingCompany.setDescription(theCompany.getDescription());
//            existingCompany.setLogo(theCompany.getLogo());

            return companyRepository.save(existingCompany);
        } else {
            throw new EntityNotFoundException("Company with ID " + theCompany.getId() + " not found");
        }
    }

    @Override
    public Company findCompanyByEmail(String theEmail) {
        return companyRepository.findCompanyByEmail(theEmail);
    }

    @Override
    public void saveCompany(Company theCompany) {
        companyRepository.save(theCompany);
    }

    @Override
    public void saveFile(MultipartFile file, String logoPath) throws IOException {
        String imagesDirectory = "src/main/resources/static/";

        Path filePath = Paths.get(imagesDirectory, logoPath);
        file.transferTo(filePath);
    }


//    @Override
//    public List<Company> findProminentCompaniesByMostPeopleApply() {
//        return companyRepository.findProminentCompaniesByMostPeopleApply();
//    }


}
