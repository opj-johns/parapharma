package com.example.parapharma.service;

import com.example.parapharma.domain.Category;
import com.example.parapharma.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    private static String BASE_PATH = "C:/Users/JONN OPPONG/IdeaProjects/parapharma/src/main/resources/assets/";

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    // get all
    public List<Category> getCategories(){
        return this.categoryRepository.findAll();
    }

    // get one
    public Category getCategory(Long categoryId){
        return this.categoryRepository.getReferenceById(categoryId);
    }

    // save one
    public Category saveCategory(Category category){
        return this.categoryRepository.save(category);
    }

    // delete one
    public void deleteCategory(Category category){
        this.categoryRepository.delete(category);
    }


}
