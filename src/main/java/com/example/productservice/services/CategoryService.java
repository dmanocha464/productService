package com.example.productservice.services;

import com.example.productservice.models.Category;
import com.example.productservice.models.Product;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();

    List<Product> getProductsInCategory(String categoryType);
}
