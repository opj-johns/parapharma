package com.example.parapharma.controller;

import com.example.parapharma.domain.Purchase;
import com.example.parapharma.domain.PurchaseDetail;
import com.example.parapharma.domain.datamodels.DialogProduct;
import com.example.parapharma.domain.datamodels.PurchaseDateUpdateDecision;
import com.example.parapharma.service.PurchaseDetailService;
import com.example.parapharma.service.PurchaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@Controller
@CrossOrigin(origins = {"http://localhost:4200", "https://parapharma-82f7f.web.app"})
@RequestMapping("/api/purchase-detail")
public class PurchaseDetailController {

    private final PurchaseDetailService purchaseDetailService;
    private final PurchaseService purchaseService;

    public PurchaseDetailController(PurchaseDetailService purchaseDetailService, PurchaseService purchaseService) {
        this.purchaseDetailService = purchaseDetailService;
        this.purchaseService = purchaseService;
    }

    @RequestMapping("/dialog-products")
    public ResponseEntity<List<DialogProduct>> fetchDialogProducts(@RequestBody Purchase purchase){
        List<DialogProduct> dialogProducts = this.purchaseDetailService.getDialogProduct(purchase);
        return ResponseEntity.ok(dialogProducts);
    }

    @RequestMapping("/update")
    public ResponseEntity<List<PurchaseDetail>> updateOrderDetails(@RequestBody List<PurchaseDetail> purchaseDetails){
        PurchaseDateUpdateDecision decision = this.purchaseDetailService.updatePurchaseDetails(purchaseDetails);


        if(decision.isUpdatePurchaseDate()){

            decision.getPurchaseDetails()
                    .get(0)
                    .getPurchase()
                    .setDate(new Date());

            this.purchaseService.savePurchase(
                    decision.getPurchaseDetails()
                            .get(0).getPurchase()
            );
        }

        return ResponseEntity.ok(decision.getPurchaseDetails());
    }
}
