package com.example.jobsearchflatform.dao;

import com.example.jobsearchflatform.entity.Cv;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CvRepository extends JpaRepository<Cv, Integer> {
}
