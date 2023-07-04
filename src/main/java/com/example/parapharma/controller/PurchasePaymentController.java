package com.example.parapharma.controller;

import com.example.parapharma.domain.*;
import com.example.parapharma.domain.datamodels.PaymentDialogData;
import com.example.parapharma.service.PurchasePaymentService;
import com.example.parapharma.service.PurchaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@CrossOrigin(origins = {"http://localhost:4200", "https://parapharma-82f7f.web.app"})
@RequestMapping("/api/purchase-payment")
public class PurchasePaymentController {

    private PurchasePaymentService purchasePaymentService;
    private PurchaseService purchaseService;

    public PurchasePaymentController(PurchasePaymentService purchasePaymentService,
                                     PurchaseService purchaseService) {
        this.purchasePaymentService = purchasePaymentService;
        this.purchaseService = purchaseService;
    }

    @RequestMapping("/save")
    public ResponseEntity<PurchasePayment> makePayment(@RequestBody PurchasePayment newPayment){
        Purchase purchase = purchaseService.getPurchase(newPayment.getPurchase().getId());

        newPayment.setPurchase(purchase);

        PurchasePayment savedPayment = this.purchasePaymentService.savePayment(newPayment);
        return ResponseEntity.ok(savedPayment);
    }

    @RequestMapping("/dialog-data")
    public ResponseEntity<List<PaymentDialogData>> fetchPaymentDialogData(@RequestBody Purchase purchase){
        List<PaymentDialogData> paymentDialogs = this.purchasePaymentService.getPaymentDialogData(purchase);
        return ResponseEntity.ok(paymentDialogs);
    }
    @RequestMapping("/types")
    public ResponseEntity<List<PaymentType>> fetchPaymentTypes(){
        List<PaymentType> paymentTypes = this.purchasePaymentService.getPaymentTypes();
        return ResponseEntity.ok(paymentTypes);
    }
}
