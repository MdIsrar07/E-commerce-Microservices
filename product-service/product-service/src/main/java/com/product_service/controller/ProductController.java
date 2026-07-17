package com.product_service.controller;


import com.product_service.dto.ApiResponse;
import com.product_service.dto.CategoryDto;
import com.product_service.dto.ProductDto;
import com.product_service.service.CategoryService;
import com.product_service.service.ProductService;
import com.product_service.service.S3service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private CategoryService categoryService;
    private ProductService productService;

    private S3service s3service;


    public ProductController(CategoryService categoryService, ProductService productService, S3service s3service) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.s3service = s3service;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<String>> addProduct(){
        ApiResponse<String> response=new ApiResponse<>();
        response.setMessage("All categories data fatched");
        response.setStatus(201);
        response.setData("Prodcut add succssful addded");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/list/categories")
    public ResponseEntity<ApiResponse<List<CategoryDto>>> getCategories(){
        List<CategoryDto> categoriesDto = categoryService.findAll();
        ApiResponse<List<CategoryDto>> response=new ApiResponse<>();
        if (categoriesDto != null){
            response.setMessage("All categories data fatched");
            response.setStatus(200);
            response.setData(categoriesDto);
            return new ResponseEntity<>(response, HttpStatus.OK);

        }
        response.setMessage("No categories data found");
        response.setStatus(500);
        response.setData(categoriesDto);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);



    }

    @GetMapping("/list/search")
    public  ResponseEntity<?> searchProducts(

            @RequestParam String keyword
    ) {
        List<ProductDto> productDtos = productService.searchProducts(keyword);

        ApiResponse<List<ProductDto>> response = new ApiResponse<>();
        if (productDtos != null && !productDtos.isEmpty()) {
            response.setMessage("all data fetched:");
            response.setStatus(200);
            response.setData(productDtos);
            return new ResponseEntity<>(productDtos, HttpStatus.OK);
        } else {
            response.setMessage(" not  data found:");
            response.setStatus(404);
            response.setData(productDtos);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }



    //http://localhost:8082/api/v1/prodcuts/upload
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile[] files,
                                             @RequestParam("brandId") long brandId
    ) {

        ArrayList<String> imagePath=new ArrayList<>();
        try {
            for(MultipartFile file:files){
                String url = s3service.uploadFile(file,brandId);
                if (url !=null){
                    imagePath.add(url);
                }
            }

            return ResponseEntity.ok("File uploaded successfully: " + imagePath);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Upload failed: " + e.getMessage());
        }
    }


}

