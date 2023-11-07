package com.example.jobsearchflatform.entity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
@Table(name="company")
public class Company {
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
    @Column(name="logo")
    private String logo;
    @Column(name="company_name")
    private String companyName;
    @Column(name="phone_number")
    private String phoneNumber;
    @Column(name="status")
    private int status;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "company",
                cascade = {CascadeType.DETACH, CascadeType.MERGE,
                        CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Recruitment> recruitments;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH,CascadeType.REFRESH,
                    CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(
            name="follow_company",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> userList;

    public Company() {
    }

    public Company(String address, String description, String email, String logo, String companyName, String phoneNumber, int status) {
        this.address = address;
        this.description = description;
        this.email = email;
        this.logo = logo;
        this.companyName = companyName;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Recruitment> getRecruitments() {
        return recruitments;
    }

    public void setRecruitments(List<Recruitment> recruitments) {
        this.recruitments = recruitments;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public void addUser(User theUser) {
        if(userList == null) {
            userList = new ArrayList<>();
        }
        userList.add(theUser);
    }

    public void removeUser(User theUser) {
        Iterator<User> iterator = userList.iterator();
        while(iterator.hasNext()) {
            User user = iterator.next();
            if(user.getEmail().equals(theUser.getEmail())) {
                iterator.remove(); // Safe removal using the iterator
            }
        }
    }
}
