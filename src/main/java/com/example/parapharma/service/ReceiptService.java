package com.example.parapharma.service;

import com.example.parapharma.domain.Receipt;
import com.example.parapharma.domain.ShopOrder;
import com.example.parapharma.repository.ReceiptRepository;
import org.springframework.stereotype.Service;
@Service
public class ReceiptService {

    private  final ReceiptRepository receiptRepository;

    public ReceiptService(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }
    public Receipt saveReceipt(Receipt receipt){
        // check if the receipt has been saved already
        Receipt savedReceipt = this.receiptRepository.findReceiptByShopOrder(receipt.getShopOrder());
        if(savedReceipt==null){
             savedReceipt= this.receiptRepository.save(receipt);
        }
        return savedReceipt;
    }

    public Receipt getReceipt(ShopOrder order){
        return this.receiptRepository.findReceiptByShopOrder(order);
    }
}
