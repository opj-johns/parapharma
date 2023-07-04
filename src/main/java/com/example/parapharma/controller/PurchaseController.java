package com.example.parapharma.controller;

import com.example.parapharma.domain.Purchase;
import com.example.parapharma.domain.Supplier;
import com.example.parapharma.domain.datamodels.PurchaseTableData;
import com.example.parapharma.domain.datamodels.PurchaseWrapper;
import com.example.parapharma.service.PurchaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@CrossOrigin(origins = {"http://localhost:4200", "https://parapharma-82f7f.web.app"})
@RequestMapping("/api/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @RequestMapping("/save")
    public ResponseEntity<Purchase> savePurchase(@RequestBody PurchaseWrapper wrapper){
        Purchase savedPurchase = this.purchaseService.savePurchase(wrapper);
        return ResponseEntity.ok(savedPurchase);
    }

    @RequestMapping("/data")
    public ResponseEntity<List<PurchaseTableData>> fetchPurchaseData(){
        List<PurchaseTableData> purchaseTableData = this.purchaseService.getPurchaseTableData();
        return ResponseEntity.ok(purchaseTableData);
    }

    @RequestMapping("/delete")
    public ResponseEntity<?> deletePurchase(@RequestBody Purchase purchase){
        this.purchaseService.deletePurchase(purchase);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping("/get-supplier")
    public ResponseEntity<Supplier> fetchSupplier(@RequestBody Purchase purchase){
        Supplier supplier = this.purchaseService.getSupplier(purchase);
        return ResponseEntity.ok(supplier);
    }

}
