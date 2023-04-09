package com.example.parapharma.service;

import com.example.parapharma.domain.OrderDetail;
import com.example.parapharma.domain.Purchase;
import com.example.parapharma.domain.PurchaseDetail;
import com.example.parapharma.domain.ShopOrder;
import com.example.parapharma.domain.datamodels.DialogProduct;
import com.example.parapharma.domain.datamodels.OrderDateUpdateDecision;
import com.example.parapharma.domain.datamodels.PurchaseDateUpdateDecision;
import com.example.parapharma.domain.datamodels.PurchaseWrapper;
import com.example.parapharma.repository.PurchaseDetailRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseDetailService {

    private PurchaseDetailRepository purchaseDetailRepository;

    public PurchaseDetailService(PurchaseDetailRepository purchaseDetailRepository) {
        this.purchaseDetailRepository = purchaseDetailRepository;
    }

    List<PurchaseDetail> savedDetails(PurchaseWrapper wrapper){
        List<PurchaseDetail> preparedDetails = new ArrayList<>();

        for(PurchaseDetail detail: wrapper.getPurchaseDetails()){
            detail.setPurchase(wrapper.getPurchase());
            preparedDetails.add(detail);
        }
        return this.purchaseDetailRepository.saveAll(preparedDetails);
    }

    public List<PurchaseDetail> getAll(Purchase purchase){
        return this.purchaseDetailRepository.findPurchaseDetailsByPurchase(purchase);
    }

    public List<DialogProduct> getDialogProduct(Purchase purchase){
        List<PurchaseDetail> purchaseDetails = this.purchaseDetailRepository
                .findPurchaseDetailsByPurchase(purchase);

        List<DialogProduct> dialogProducts = new ArrayList<>();

        for(PurchaseDetail purchaseDetail : purchaseDetails){

            DialogProduct dialogProduct = new DialogProduct();

            dialogProduct.setProductId(purchaseDetail.getProduct().getId());
            dialogProduct.setProductName(purchaseDetail.getProduct().getName());
            dialogProduct.setQuantity(purchaseDetail.getQuantity());
            dialogProduct.setPrice(purchaseDetail.getPurchasePrice());
            dialogProduct.setProductUrl(purchaseDetail.getProduct().getImageUrl());
            dialogProducts.add(dialogProduct);
        }

        return dialogProducts;
    }

    void deleteDetails(Purchase purchase){
        this.purchaseDetailRepository.deletePurchaseDetailsByPurchase(purchase);
    }

    public PurchaseDateUpdateDecision updatePurchaseDetails(List<PurchaseDetail> purchaseDetails){

        // get all order details with the order of the incoming orderDetails
        // they all have one order (with one orderId)
        List<PurchaseDetail> existingPurchaseDetails = this.getAll(purchaseDetails.get(0).getPurchase());

        // check the products in "orderDetails" and "existingOrderDetails"

        // if they are the same, increase the "quantity" variable
        // of "existingOrderDetails" and remove it from "orderDetail"

        // else

//        List<Long> oldProdIds = new ArrayList<>();
        boolean updatePurchaseDate = false;


        for(int inIndex=0; inIndex<purchaseDetails.size(); inIndex++){
            for(int oldIndex=0; oldIndex< existingPurchaseDetails.size(); oldIndex++){
                if(existingPurchaseDetails.get(oldIndex).getProduct().getId()
                                .equals(purchaseDetails.get(inIndex).getProduct().getId())
                        ){
                    // set id for incoming
                    purchaseDetails.get(inIndex).setId(
                            existingPurchaseDetails.get(oldIndex).getId()
                    );
                    // increase quantity of incoming
                    purchaseDetails.get(inIndex).setQuantity(
                            purchaseDetails.get(inIndex).getQuantity()+
                                    existingPurchaseDetails.get(oldIndex).getQuantity()
                    );
                    // set updateOrderID
                    updatePurchaseDate = true;
                }
            }
        }

        PurchaseDateUpdateDecision purchaseDateUpdateDecision = new PurchaseDateUpdateDecision();
        purchaseDateUpdateDecision.setPurchaseDetails(
                this.purchaseDetailRepository.saveAll(purchaseDetails)
        );
        purchaseDateUpdateDecision.setUpdatePurchaseDate(updatePurchaseDate);

        return purchaseDateUpdateDecision;
    }


}
