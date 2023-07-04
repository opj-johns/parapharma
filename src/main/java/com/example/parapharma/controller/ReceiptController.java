package com.example.parapharma.controller;

import com.example.parapharma.domain.Receipt;
import com.example.parapharma.domain.ShopOrder;
import com.example.parapharma.service.ReceiptService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin(origins = {"http://localhost:4200", "https://parapharma-82f7f.web.app"})
@RequestMapping("/api/receipt")
public class ReceiptController {

    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @RequestMapping("/save")
    public ResponseEntity<Receipt> saveNewReceipt(@RequestBody Receipt receipt){
        Receipt savedReceipt = this.receiptService.saveReceipt(receipt);
        return ResponseEntity.ok(savedReceipt);
    }

    @RequestMapping("/fetch-by-order")
    public ResponseEntity<Receipt> fetchReceipt(@RequestBody ShopOrder order){
        Receipt receipt = this.receiptService.getReceipt(order);
        return ResponseEntity.ok(receipt);
    }

}
