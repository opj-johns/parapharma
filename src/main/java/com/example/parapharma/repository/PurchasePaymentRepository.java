package com.example.parapharma.repository;

import com.example.parapharma.domain.Purchase;
import com.example.parapharma.domain.PurchasePayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchasePaymentRepository extends JpaRepository<PurchasePayment, Long> {
    List<PurchasePayment> findPurchasePaymentsByPurchase(Purchase purchase);

    void deletePurchasePaymentsByPurchase(Purchase purchase);
}
