package com.example.parapharma.service;

import com.example.parapharma.domain.ProductPhoto;
import com.example.parapharma.repository.ProductPhotoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class ProductPhotoService {

    private ProductPhotoRepository productPhotoRepository;
    private CategoryService categoryService;

    private static String BASE_PATH = "C:/Users/JONN OPPONG/IdeaProjects/parapharma/src/main/resources/assets/";

    public ProductPhotoService(ProductPhotoRepository productPhotoRepository,
                               CategoryService categoryService){
        this.productPhotoRepository = productPhotoRepository;
        this.categoryService = categoryService;
    }

    public String uploadImageToFileSystem(MultipartFile file) throws IOException {

        String imageAbsolutePath = BASE_PATH + file.getOriginalFilename();

        // save image to file system
        file.transferTo(new File(imageAbsolutePath));

        // save image data
        ProductPhoto productPhoto = this.productPhotoRepository.save(ProductPhoto.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .filePath(imageAbsolutePath).build()
        );

        if(productPhoto != null){
            return "File uploaded successfully "  + imageAbsolutePath;
        }else{
            return "Failed to upload file";
        }

    }


    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {


        // get image file path
        String filePath =BASE_PATH + fileName;

        // read bytes from path
        byte[] photo = Files.readAllBytes(new File(filePath).toPath());

        return photo;

    }


}
