package com.example.productservice.services;

import com.example.productservice.dtos.ProductDto;
import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreCategoryServiceImpl implements CategoryService{
    private RestTemplateBuilder restTemplateBuilder;

    public FakeStoreCategoryServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }
    @Override
    public List<Category> getAllCategories() {
        RestTemplate restTemplate = restTemplateBuilder.build();

        ResponseEntity<String[]> l = restTemplate.getForEntity(
                "https://fakestoreapi.com/products/categories",
                String[].class
        );
        List<Category> answer = new ArrayList<>();

        for(String val: l.getBody()) {
            Category category = new Category();
            category.setName(val);
            answer.add(category);
        }
        return answer;
    }

    @Override
    public List<Product> getProductsInCategory(String categoryType) {
        RestTemplate restTemplate = restTemplateBuilder.build();

        ResponseEntity<ProductDto[]> l = restTemplate.getForEntity(
                "https://fakestoreapi.com/products/category/{type}",
                ProductDto[].class, categoryType
        );
        List<Product> answer = new ArrayList<>();

        for (ProductDto productDto: l.getBody()) {
            Product product = new Product();
            product.setId(productDto.getId());
            product.setTitle(productDto.getTitle());
            product.setPrice(productDto.getPrice());
            Category category = new Category();
            category.setName(productDto.getCategory());
            product.setCategory(category);
            product.setImageUrl(productDto.getImage());
            product.setDescription(productDto.getDescription());
            if(productDto.getRating() != null) {
                product.setRating(productDto.getRating().getRate());
                product.setRatingCount(productDto.getRating().getCount());
            }
            answer.add(product);
        }
        return answer;
    }
}
