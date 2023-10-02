package com.example.productservice.services;

import com.example.productservice.dtos.ProductDto;
import com.example.productservice.models.Product;
import org.springframework.http.HttpStatusCode;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAllProducts();

    Optional<Product> getSingleProduct(Long productId);

    Product addNewProduct(ProductDto product);

    Product updateProduct(Long productId, ProductDto product);
    Product replaceProduct(Long productId, ProductDto product);

    HttpStatusCode deleteProduct(Long productId);
}
