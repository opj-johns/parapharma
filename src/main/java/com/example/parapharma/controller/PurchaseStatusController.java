package com.example.parapharma.controller;

import com.example.parapharma.service.PurchaseStatusService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin(origins = {"http://localhost:4200", "https://parapharma-82f7f.web.app"})
public class PurchaseStatusController {

    private PurchaseStatusService purchaseStatusService;

    public PurchaseStatusController(PurchaseStatusService purchaseStatusService) {
        this.purchaseStatusService = purchaseStatusService;
    }

}
