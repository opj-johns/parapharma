package com.example.parapharma.controller;

import com.example.parapharma.domain.Promotion;
import com.example.parapharma.service.PromotionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/promotion")
public class PromotionController {

    private PromotionService promotionService;

    public PromotionController(PromotionService promotionService){
        this.promotionService = promotionService;
    }

    // get all
    @RequestMapping("/all")
    public ResponseEntity<List<Promotion>> getAll(){
        List<Promotion> promotions = this.promotionService.getPromotions();
        return ResponseEntity.ok(promotions);
    }

    @RequestMapping("/{id}")
    public ResponseEntity<Promotion> getPromotion(@PathVariable Long id){
        Promotion promotion = this.promotionService.fetchPromotion(id);
        return ResponseEntity.ok(promotion);
    }

    @RequestMapping("/save")
    public ResponseEntity<Promotion> savePromotion(@RequestBody Promotion promo){
        Promotion promotion = this.promotionService.savePromotion(promo);
        return ResponseEntity.ok(promotion);
    }

    @RequestMapping("/delete")
    public ResponseEntity<?> deletePromo(@RequestBody Promotion promotion){
        this.promotionService.deletePromotion(promotion);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
