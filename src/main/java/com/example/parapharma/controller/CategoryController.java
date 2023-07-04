package com.example.parapharma.controller;

import com.example.parapharma.domain.Category;
import com.example.parapharma.domain.ERole;
import com.example.parapharma.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@CrossOrigin(origins = {"http://localhost:4200", "https://parapharma-82f7f.web.app"})
@RequestMapping("/api/categories")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    // get all
    @RequestMapping("/all")
    public ResponseEntity<List<Category>> fetchCategories(){
        List<Category> categories = this.categoryService.getCategories();
        return ResponseEntity.ok(categories);
    }

    // get one
    @RequestMapping("/{id}")
    public ResponseEntity<Category> fetchCategory(@PathVariable("id") Long id){
        Category category = this.categoryService.getCategory(id);
        return ResponseEntity.ok(category);
    }

    // save one
    @RequestMapping("/save")
    public ResponseEntity<Category> saveUpdateCategory(@RequestBody Category category){
        Category savedCategory = this.categoryService.saveCategory(category);
        return ResponseEntity.ok(savedCategory);
    }

    // delete one
    @RequestMapping("/delete")
    public ResponseEntity<?> deleteCategory(@RequestBody Category category){
        this.categoryService.deleteCategory(category);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
