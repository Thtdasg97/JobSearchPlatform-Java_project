package com.example.springasm02.service;

import com.example.springasm02.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAllCategories();
    Category findCategoryById(int categoryId);
    List<Category> findTop4CategoriesByNumberChoose();
}
