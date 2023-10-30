package com.example.springasm02.entity;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
@Table(name="recruitment")
public class Recruitment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "experience")
    private String experience;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "address")
    private String address;
    @Column(name = "deadline")
    private String deadline;
    @Column(name="salary")
    private String salary;
    @Column(name = "created_at")
    private String createdAt;
    @Column(name = "type")
    private String type;
    @Column(name = "job_rank")
    private String jobRank;
    @Column(name = "status")
    private int status;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name="company_id")
    private Company company;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name="category_id")
    private Category category;
    @OneToMany(mappedBy = "recruitment", cascade = CascadeType.ALL)
    private List<ApplyPost> applyPosts;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH,CascadeType.REFRESH,
                    CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(
            name="save_job",
            joinColumns = @JoinColumn(name = "recruitment_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> userList;

    public Recruitment() {
    }

    public Recruitment(String title, String description, String experience, Integer quantity, String address, String deadline, String salary, String createdAt, String type, String jobRank, int status) {
        this.title = title;
        this.description = description;
        this.experience = experience;
        this.quantity = quantity;
        this.address = address;
        this.deadline = deadline;
        this.salary = salary;
        this.createdAt = createdAt;
        this.type = type;
        this.jobRank = jobRank;
        this.status = status;
    }

// Getters and setters

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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getJobRank() {
        return jobRank;
    }

    public void setJobRank(String jobRank) {
        this.jobRank = jobRank;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDeadline() {
        return deadline;
    }
    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public void addApplyPost(ApplyPost tempApplyPost) {
        if(applyPosts == null) {
            applyPosts = new ArrayList<>();
        }

        applyPosts.add(tempApplyPost);
        tempApplyPost.setRecruitment(this);
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
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getEmail().equals(theUser.getEmail())) {
                iterator.remove(); // Safe removal using the iterator
            }
        }
    }

}
