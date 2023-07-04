package com.example.parapharma.service;

import com.example.parapharma.domain.ProductPhoto;
import com.example.parapharma.repository.ProductPhotoRepository;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class ProductPhotoService {

    private ProductPhotoRepository productPhotoRepository;
    private CategoryService categoryService;
    private ResourceLoader resourceLoader;

    private static String BASE_PATH = "../../../../../resources/assets/";

    public ProductPhotoService(ProductPhotoRepository productPhotoRepository,
                               CategoryService categoryService,
                               ResourceLoader resourceLoader){
        this.productPhotoRepository = productPhotoRepository;
        this.categoryService = categoryService;
        this.resourceLoader = resourceLoader;
    }

    public String uploadImageToFileSystem(MultipartFile imgFile) throws IOException, URISyntaxException {

        String fileName = imgFile.getOriginalFilename();
        // Get a reference to the assets folder
        File assetsFolder = new File((resourceLoader.getResource("classpath:assets/").getURI()));

        // Create a new file within the assets folder
        File newFile = new File(assetsFolder, fileName);

//        File temFile = new File(resourceLoader.getResource("classpath:assets/").getURI());
        ProductPhoto productPhoto=null;
        // Check if the file already exists
            if(!newFile.exists()){
                boolean success = newFile.createNewFile();
                File tempFile = new File(resourceLoader.getResource("classpath:assets/" + fileName).getURI());
                imgFile.transferTo(tempFile);
                System.out.println("success = "+success);
                if(!success){
                    throw new IOException("Failed to create file: " + newFile.getAbsolutePath());
                }
                productPhoto = this.productPhotoRepository.save(ProductPhoto.builder()
                        .name(fileName)
                        .type(imgFile.getContentType())
                        .filePath(newFile.getPath()).build()
                );
            }




        // save image to file system
//        imgFile.transferTo(temFile);

        // save image data


        if(productPhoto != null){
            return "File uploaded successfully "  + newFile.getPath();
        }else{
            return "Failed to upload file";
        }

    }


    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {


        // get image file path
        File tempFile = new File(resourceLoader.getResource("classpath:assets/" + fileName).getURI());

        // read bytes from path
        byte[] photo = Files.readAllBytes(tempFile.toPath());

        return photo;

    }


}
