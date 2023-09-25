package com.example.productservice.controllers;

import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import com.example.productservice.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products/category")
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping()
    public ResponseEntity<List<Category>> getAllCategories() {
        ResponseEntity<List<Category>> response = new ResponseEntity(
                categoryService.getAllCategories(),
                HttpStatus.OK
        );
        return response;
    }

    @GetMapping("/{categoryType}")
    public ResponseEntity<List<Product>> getProductsInCategory(@PathVariable("categoryType") String categoryType) {
        ResponseEntity<List<Product>> response = new ResponseEntity(
                categoryService.getProductsInCategory(categoryType),
                HttpStatus.OK
        );
        return response;
    }
}
