package com.example.parapharma.service;

import com.example.parapharma.domain.PurchaseStatus;
import com.example.parapharma.repository.PurchaseDetailRepository;
import com.example.parapharma.repository.PurchaseStatusRepository;
import org.springframework.stereotype.Service;

@Service
public class PurchaseStatusService {

    private PurchaseStatusRepository purchaseStatusRepository;

    public PurchaseStatusService(PurchaseStatusRepository purchaseStatusRepository) {
        this.purchaseStatusRepository = purchaseStatusRepository;
    }

    public PurchaseStatus findStatus(Long id){
        return this.purchaseStatusRepository.findPurchaseStatusById(id);
    }

}
