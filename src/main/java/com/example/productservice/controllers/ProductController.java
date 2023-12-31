package com.example.productservice.controllers;

import com.example.productservice.dtos.ErrorResponseDto;
import com.example.productservice.dtos.ProductDto;
import com.example.productservice.exceptions.NotFoundException;
import com.example.productservice.models.Product;
import com.example.productservice.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;

    public ProductController( ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public ResponseEntity<List<Product>> getAllProducts() {
        ResponseEntity<List<Product>> response = new ResponseEntity(
                productService.getAllProducts(),
                HttpStatus.OK
        );
        return response;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getSingleProduct(@PathVariable("productId") Long productId) throws NotFoundException {

        Optional<Product> product = productService.getSingleProduct(productId);
        if(product.isEmpty()) {
            throw new NotFoundException("No product with id "+productId);
        }
        ResponseEntity<Product> response = new ResponseEntity(
                product.get(),
                HttpStatus.OK
        );
        return response;
    }


    @PostMapping()
    public ResponseEntity<Product> addNewProduct(@RequestBody ProductDto productDto) {
        Product newProduct = productService.addNewProduct(productDto);

        ResponseEntity<Product> response = new ResponseEntity<>(newProduct, HttpStatus.OK);

        return response;
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> replaceProduct(@PathVariable("productId") Long productId, @RequestBody ProductDto productDto) {
        ResponseEntity<Product> response = new ResponseEntity(
                productService.replaceProduct(productId, productDto),
                HttpStatus.OK
        );
        return response;
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable("productId") Long productId, @RequestBody ProductDto productDto) {
        ResponseEntity<Product> response = new ResponseEntity(
                productService.updateProduct(productId, productDto),
                HttpStatus.OK
        );
        return response;
    }

    @DeleteMapping("/{productId}")
    public String deleteProduct(@PathVariable("productId") Long productId) {
        HttpStatusCode statusCode = productService.deleteProduct(productId);
        return "Deleted product with status code: " + statusCode;
    }

   /* @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> HandelNotFoundException(Exception exception) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setErrorMessage(exception.getMessage());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

    */
}
