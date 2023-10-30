package com.example.springasm02.service;

import com.example.springasm02.dao.CategoryRepository;
import com.example.springasm02.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository theCategoryRepository) {
        categoryRepository = theCategoryRepository;
    }

    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findCategoryById(int categoryId) {
        Optional<Category> result = categoryRepository.findById(categoryId);

        Category theCategory = null;

        if(result.isPresent()) {
            theCategory = result.get();
        } else {
            throw new RuntimeException("Did not find Category id = " + categoryId);
        }
        return theCategory;
    }

    @Override
    public List<Category> findTop4CategoriesByNumberChoose() {
        List<Category> allCategories = categoryRepository.findAll();

        List<Category> sortedCategories = allCategories.stream()
                .sorted(Comparator.comparingInt(Category::getNumberChoose).reversed())
                .collect(Collectors.toList());

        return sortedCategories.stream().limit(4).collect(Collectors.toList());



    }


}
