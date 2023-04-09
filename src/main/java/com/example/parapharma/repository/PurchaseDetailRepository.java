package com.example.parapharma.repository;

import com.example.parapharma.domain.Purchase;
import com.example.parapharma.domain.PurchaseDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PurchaseDetailRepository extends JpaRepository<PurchaseDetail, Long> {
    List<PurchaseDetail> findPurchaseDetailsByPurchase(Purchase purchase);
    void deletePurchaseDetailsByPurchase(Purchase purchase);
}
