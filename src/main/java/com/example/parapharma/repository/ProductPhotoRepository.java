package com.example.parapharma.repository;

import com.example.parapharma.domain.ProductPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductPhotoRepository extends JpaRepository<ProductPhoto, Long> {
    Optional<ProductPhoto> findByName(String fileName);
}
