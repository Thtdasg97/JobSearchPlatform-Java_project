package com.example.jobsearchflatform.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="address")
    private String address;
    @Column(name="description")
    private String description;
    @Column(name="email")
    private String email;
    @Column(name="full_name")
    private String fullName;
    @Column(name="image")
    private String image;
    @Column(name="password")
    private String password;
    @Column(name="phone_number")
    private String phoneNumber;
    @Column(name="status")
    private int status;
    @OneToMany(mappedBy = "user", cascade = {
            CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    private List<ApplyPost> applyPosts;

    @ManyToOne(cascade = {
            CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name="role_id")
    private Role role;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="cv_id")
    private Cv cv;
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH,CascadeType.REFRESH,
                    CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(
            name="save_job",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "recruitment_id")
    )
    private List<Recruitment> recruitmentList;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH,CascadeType.REFRESH,
                    CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(
            name="follow_company",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "company_id")
    )
    private List<Company> companyList;

    public User() {
    }

    public User(String address, String description, String email, String fullName, String image, String password, String phoneNumber, int status) {
        this.address = address;
        this.description = description;
        this.email = email;
        this.fullName = fullName;
        this.image = image;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ApplyPost> getApplyPosts() {
        return applyPosts;
    }

    public void setApplyPosts(List<ApplyPost> applyPosts) {
        this.applyPosts = applyPosts;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Cv getCv() {
        return cv;
    }

    public void setCv(Cv cv) {
        this.cv = cv;
    }

    public List<Recruitment> getRecruitmentList() {
        return recruitmentList;
    }

    public void setRecruitmentList(List<Recruitment> recruitmentList) {
        this.recruitmentList = recruitmentList;
    }

    public void addRecruitment(Recruitment theRecruitment) {
        if(recruitmentList == null) {
            recruitmentList = new ArrayList<>();
        }
        recruitmentList.add(theRecruitment);
    }

    public List<Company> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<Company> companyList) {
        this.companyList = companyList;
    }

    public void addApplyPost(ApplyPost theApplyPost) {
        if(applyPosts == null) {
            applyPosts = new ArrayList<>();
        }
        applyPosts.add(theApplyPost);
    }
}
