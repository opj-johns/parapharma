package com.example.parapharma.repository;

import com.example.parapharma.domain.PurchaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseStatusRepository extends JpaRepository<PurchaseStatus, Long> {
    PurchaseStatus findPurchaseStatusById(Long id);
}
