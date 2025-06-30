package com.princez1.ZShop.service.impl;

import com.princez1.ZShop.dtos.CategoryDTO;
import com.princez1.ZShop.entities.Category;
import com.princez1.ZShop.entities.Product;
import com.princez1.ZShop.repositories.CategoryRepository;
import com.princez1.ZShop.repositories.ProductRepository;
import com.princez1.ZShop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    @Override
    @Transactional
    public Category createCategory(CategoryDTO categoryDTO) {
        Category newCategory = Category
                .builder()
                .name(categoryDTO.getName())
                .build();
        return categoryRepository.save(newCategory);
    }

    @Override
    public Category getCategoryById(long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    @Transactional
    public Category updateCategory(long categoryId,
                                   CategoryDTO categoryDTO) {
        Category existingCategory = getCategoryById(categoryId);
        existingCategory.setName(categoryDTO.getName());
        categoryRepository.save(existingCategory);
        return existingCategory;
    }

    @Override
    @Transactional
    public Category deleteCategory(long id) throws Exception {
        Category category = categoryRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        List<Product> products = productRepository.findByCategory(category);
        if (!products.isEmpty()) {
            throw new IllegalStateException("Cannot delete category with associated products");
        } else {
            categoryRepository.deleteById(id);
            return  category;
        }
    }
}
