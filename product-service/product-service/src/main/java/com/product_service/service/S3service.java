package com.product_service.service;



import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3service {
    public String uploadFile(MultipartFile file, long brandId) throws IOException;
}

