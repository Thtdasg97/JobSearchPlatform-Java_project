package com.example.jobsearchflatform.service;

import com.example.jobsearchflatform.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAllCategories();
    Category findCategoryById(int categoryId);
    List<Category> findTop4CategoriesByNumberChoose();
}
