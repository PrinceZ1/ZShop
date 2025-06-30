package com.princez1.ZShop.service;

import com.princez1.ZShop.dtos.CategoryDTO;
import com.princez1.ZShop.entities.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryDTO category);
    Category getCategoryById(long id);
    List<Category> getAllCategories();
    Category updateCategory(long categoryId, CategoryDTO category);
    Category deleteCategory(long id) throws Exception;
}
