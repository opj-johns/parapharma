package com.example.parapharma.repository;

import com.example.parapharma.domain.Purchase;
import com.example.parapharma.domain.PurchaseReceipt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseReceiptRepository extends JpaRepository<PurchaseReceipt, Long> {
    PurchaseReceipt findPurchaseReceiptByPurchase(Purchase purchase);
}
