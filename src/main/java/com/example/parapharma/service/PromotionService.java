package com.example.parapharma.service;

import com.example.parapharma.domain.Promotion;
import com.example.parapharma.repository.ProductRepository;
import com.example.parapharma.repository.PromotionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionService {

    private PromotionRepository promotionRepository;

    public PromotionService(PromotionRepository promotionRepository){
        this.promotionRepository = promotionRepository;
    }

    public List<Promotion> getPromotions(){
        return this.promotionRepository.findAll();
    }

    public Promotion savePromotion(Promotion promotion){
        return this.promotionRepository.save(promotion);
    }

    public Promotion fetchPromotion(Long promoId){
        return this.promotionRepository.getReferenceById(promoId);
    }


    public void deletePromotion(Promotion promotion){
        this.promotionRepository.delete(promotion);
    }
}
