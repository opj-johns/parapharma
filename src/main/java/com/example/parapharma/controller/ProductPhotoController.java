package com.example.parapharma.controller;


import com.example.parapharma.service.ProductPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;


@RestController
@CrossOrigin(origins = {"http://localhost:4200", "https://parapharma-82f7f.web.app"})
@RequestMapping(path = "api/image")
public class ProductPhotoController {

    @Autowired
    private ProductPhotoService productPhotoService;


    @PostMapping("/fileSystem")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> uploadImageToFileSystem(@RequestParam("image") MultipartFile file) throws IOException, URISyntaxException {
        String uploadedImage = this.productPhotoService.uploadImageToFileSystem(file);
        System.out.println("File name: "+file.getOriginalFilename());
        System.out.println("File contentType: "+ file.getContentType());
        return new ResponseEntity<>(uploadedImage, HttpStatus.OK);
    }

    @GetMapping("/fileSystem/get/{fileName}")
    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String fileName) throws IOException{
        byte[] imageByte = this.productPhotoService.downloadImageFromFileSystem(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageByte);
    }






}
