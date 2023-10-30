package com.example.springasm02.entity;

import jakarta.persistence.*;

@Entity
@Table(name="save_job")
public class SaveJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    public SaveJob() {
    }

    public SaveJob(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
