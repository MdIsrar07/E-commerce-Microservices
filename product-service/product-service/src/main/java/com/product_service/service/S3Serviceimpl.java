package com.product_service.service;




import com.product_service.entity.Brand;
import com.product_service.entity.Image;
import com.product_service.repository.BrandRepository;
import com.product_service.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3Serviceimpl implements S3service {

    private final S3Client s3Client;

    private ImageRepository imageRepository;
    private BrandRepository brandRepository;

    @Value("${aws.bucketName}")
    private String bucketName;

    @Value("${aws.region}")
    private String region;

    public S3Serviceimpl(S3Client s3Client, ImageRepository imageRepository, BrandRepository brandRepository) {
        this.s3Client = s3Client;
        this.imageRepository = imageRepository;
        this.brandRepository = brandRepository;
    }

    public String uploadFile(MultipartFile file,long brandId) throws IOException {

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(file.getContentType())
                .build();

        PutObjectResponse putObjectResponse = s3Client.putObject(
                putObjectRequest,
                RequestBody.fromBytes(file.getBytes())
        );

        String url="https://" + bucketName + ".s3.amazonaws.com/" + fileName;

        // save the images
        Brand brand = brandRepository.findById(brandId).get();
        Image image=new Image();
        image.setBrand(brand);
        image.setUrl(url);
        imageRepository.save(image);


        return url;
    }
}
