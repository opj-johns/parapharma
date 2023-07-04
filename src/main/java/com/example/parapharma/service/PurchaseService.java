package com.example.parapharma.service;

import com.example.parapharma.domain.Purchase;
import com.example.parapharma.domain.PurchaseDetail;
import com.example.parapharma.domain.PurchaseStatus;
import com.example.parapharma.domain.Supplier;
import com.example.parapharma.domain.datamodels.PurchaseTableData;
import com.example.parapharma.domain.datamodels.PurchaseWrapper;
import com.example.parapharma.repository.PurchaseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseDetailService purchaseDetailService;
    private final PurchaseStatusService purchaseStatusService;
    private final PurchasePaymentService purchasePaymentService;

    public PurchaseService(PurchaseRepository purchaseRepository,
                           PurchaseDetailService purchaseDetailService,
                           PurchaseStatusService purchaseStatusService,
                           PurchasePaymentService purchasePaymentService) {
        this.purchaseRepository = purchaseRepository;
        this.purchaseDetailService = purchaseDetailService;
        this.purchaseStatusService = purchaseStatusService;
        this.purchasePaymentService = purchasePaymentService;
    }

    public Purchase savePurchase(PurchaseWrapper purchaseWrapper){

        PurchaseStatus status = this.purchaseStatusService.findStatus(1L);

        Purchase newPurchase = purchaseWrapper.getPurchase();
        newPurchase.setStatus(status);

        Purchase savedPurchase = this.purchaseRepository.save(newPurchase);
        purchaseWrapper.setPurchase(savedPurchase);

        this.purchaseDetailService.savedDetails(purchaseWrapper);


        return  savedPurchase;

    }

    public Purchase getPurchase(Long id){
        return this.purchaseRepository.getReferenceById(id);
    }

    public void savePurchase(Purchase purchase){
         this.purchaseRepository.save(purchase);
    }


    public Supplier getSupplier(Purchase purchase){
        Purchase savedPurchase = this.purchaseRepository.getReferenceById(purchase.getId());
        return savedPurchase.getSupplier();
    }

    public List<PurchaseTableData> getPurchaseTableData(){
           List<PurchaseTableData> purchaseTableDataList = new ArrayList<>();

           List<Purchase> purchases = this.purchaseRepository.findAll();

           purchases.forEach(purchase -> {

               PurchaseTableData tableData = new PurchaseTableData();
               tableData.setDate(purchase.getDate());
               tableData.setStatus(purchase.getStatus().getStatus());
               tableData.setPurchaseId(purchase.getId());
               tableData.setSupplierName(purchase.getSupplier().getName());

               List<PurchaseDetail> purchaseDetails = this.purchaseDetailService.getAll(purchase);

               float amountPaid = this.purchasePaymentService.getAmountPaid(purchase);
               tableData.setAmountPaid(amountPaid);

               float purchaseTotalCost = 0;
               int totalProducts = 0;
               for(PurchaseDetail detail: purchaseDetails){
                   purchaseTotalCost += detail.getPurchasePrice() * detail.getQuantity();
                   totalProducts += detail.getQuantity();
               }

               tableData.setAmountUnpaid(purchaseTotalCost - amountPaid);
               tableData.setTtc(purchaseTotalCost);
               tableData.setNumberOfProducts(totalProducts);

               purchaseTableDataList.add(tableData);


           });

           return purchaseTableDataList;
    }

    public void deletePurchase(Purchase purchase){
        // delete payments made with this order
        this.purchasePaymentService.deletePayment(purchase);
        // delete orderDetails recorded with this shop order
        this.purchaseDetailService.deleteDetails(purchase);
        // delete shopOrder
        this.purchaseRepository.delete(purchase);
    }
}
