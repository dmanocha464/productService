package com.example.productservice.services;

import com.example.productservice.dtos.ProductDto;
import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductServiceImpl implements ProductService{
    private RestTemplateBuilder restTemplateBuilder;

    public FakeStoreProductServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }
    @Override
    public List<Product> getAllProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();

        ResponseEntity<ProductDto[]> l = restTemplate.getForEntity(
                "https://fakestoreapi.com/products",
                ProductDto[].class
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

    @Override
    public Product getSingleProduct(Long productId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<ProductDto> response =  restTemplate.getForEntity(
                "https://fakestoreapi.com/products/{id}",
                ProductDto.class, productId);

        ProductDto productDto = response.getBody();

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
        return product;
    }

    @Override
    public Product addNewProduct(ProductDto productRec) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<ProductDto> response =  restTemplate.postForEntity(
                "https://fakestoreapi.com/products",
                productRec, ProductDto.class);

        ProductDto productDto = response.getBody();
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
        return product;
    }

    @Override
    public Product updateProduct(Long productId, ProductDto productRec) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<ProductDto> response =  restTemplate.exchange(
                "https://fakestoreapi.com/products/{id}",
                HttpMethod.PUT, new HttpEntity<ProductDto>(productRec),
                ProductDto.class, productId);

        ProductDto productDto = response.getBody();
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
        return product;
    }

    @Override
    public HttpStatusCode deleteProduct(Long productId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Void> response =  restTemplate.exchange(
                "https://fakestoreapi.com/products/{id}",
                HttpMethod.DELETE, null,
                Void.class, productId);
        return response.getStatusCode();
    }
}
