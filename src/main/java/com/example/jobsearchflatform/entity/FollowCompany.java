package com.example.jobsearchflatform.entity;

import jakarta.persistence.*;

@Entity
@Table(name="follow_company")
public class FollowCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    public FollowCompany(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
