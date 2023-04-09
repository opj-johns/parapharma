package com.example.parapharma.controller;

import com.example.parapharma.domain.Purchase;
import com.example.parapharma.domain.PurchaseReceipt;
import com.example.parapharma.service.PurchaseReceiptService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/purchase-receipt")
public class PurchaseReceiptController {

    private final PurchaseReceiptService purchaseReceiptService;

    public PurchaseReceiptController(PurchaseReceiptService purchaseReceiptService) {
        this.purchaseReceiptService = purchaseReceiptService;
    }

    @RequestMapping("/save")
    public ResponseEntity<PurchaseReceipt> saveNewReceipt(@RequestBody PurchaseReceipt receipt){
        PurchaseReceipt savedReceipt = this.purchaseReceiptService.saveReceipt(receipt);
        return ResponseEntity.ok(savedReceipt);
    }

    @RequestMapping("/fetch-by-purchase")
    public ResponseEntity<PurchaseReceipt> fetchReceipt(@RequestBody Purchase purchase){
        PurchaseReceipt receipt = this.purchaseReceiptService.getReceipt(purchase);
        return ResponseEntity.ok(receipt);
    }
}
