package com.example.parapharma.service;

import com.example.parapharma.domain.Purchase;
import com.example.parapharma.domain.PurchaseReceipt;
import com.example.parapharma.repository.PurchaseReceiptRepository;
import org.springframework.stereotype.Service;

@Service
public class PurchaseReceiptService {
    private final PurchaseReceiptRepository purchaseReceiptRepository;

    public PurchaseReceiptService(PurchaseReceiptRepository purchaseReceiptRepository) {
        this.purchaseReceiptRepository = purchaseReceiptRepository;
    }

    public PurchaseReceipt saveReceipt(PurchaseReceipt receipt){

        // check if the receipt has been saved already
        PurchaseReceipt savedReceipt = this.purchaseReceiptRepository.
                findPurchaseReceiptByPurchase(receipt.getPurchase());
        if(savedReceipt==null){
            savedReceipt= this.purchaseReceiptRepository.save(receipt);
        }
        return savedReceipt;
    }

    public PurchaseReceipt getReceipt(Purchase purchase){
        return this.purchaseReceiptRepository.findPurchaseReceiptByPurchase(purchase);
    }

}
