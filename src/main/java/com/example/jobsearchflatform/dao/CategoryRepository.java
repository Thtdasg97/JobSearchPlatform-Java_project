package com.example.jobsearchflatform.dao;

import com.example.jobsearchflatform.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
