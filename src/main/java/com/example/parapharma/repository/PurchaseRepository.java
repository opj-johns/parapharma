package com.example.parapharma.repository;


import com.example.parapharma.domain.Purchase;
import com.example.parapharma.domain.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    Purchase findPurchaseBySupplier(Supplier supplier);
}
