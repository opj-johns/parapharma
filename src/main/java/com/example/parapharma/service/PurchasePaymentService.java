package com.example.parapharma.service;

import com.example.parapharma.domain.*;
import com.example.parapharma.domain.datamodels.PaymentDialogData;
import com.example.parapharma.repository.PaymentTypeRepository;
import com.example.parapharma.repository.PurchaseDetailRepository;
import com.example.parapharma.repository.PurchasePaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PurchasePaymentService {

    private PurchasePaymentRepository purchasePaymentRepository;
    private PaymentTypeRepository paymentTypeRepository;

    public PurchasePaymentService(PurchasePaymentRepository purchasePaymentRepository,
                                  PaymentTypeRepository paymentTypeRepository) {
        this.purchasePaymentRepository = purchasePaymentRepository;
        this.paymentTypeRepository = paymentTypeRepository;
    }

    public float getAmountPaid(Purchase purchase){
        List<PurchasePayment> payments = this.purchasePaymentRepository
                .findPurchasePaymentsByPurchase(purchase);

        float total = 0;
        if(payments.size()!=0){
            for(PurchasePayment payment : payments){
                total+=payment.getAmount_paid();
            }
        }
        return total;
    }


    public List<PaymentType> getPaymentTypes(){
        return this.paymentTypeRepository.findAll();
    }

    public PurchasePayment savePayment(PurchasePayment payment){
        payment.setDate(new Date());
        return this.purchasePaymentRepository.save(payment);
    }




    public List<PaymentDialogData> getPaymentDialogData(Purchase purchase){

        List<PurchasePayment> payments = this.purchasePaymentRepository
                .findPurchasePaymentsByPurchase(purchase);


        List<PaymentDialogData> paymentDialogs = new ArrayList<>();

        for(PurchasePayment payment : payments){
            PaymentDialogData paymentDialog = new PaymentDialogData();

            paymentDialog.setTypeOfPayment(payment.getPaymentType().getType());
            paymentDialog.setAmountPaid(payment.getAmount_paid());

            String stringDate = payment.getDate().toString();

            List<String> parts = List.of(stringDate.split(" "));

            paymentDialog.setDate(parts.get(0));
            parts = List.of(parts.get(1).split(":"));
            paymentDialog.setTime(parts.get(0)+":"+parts.get(1));

            paymentDialogs.add(paymentDialog);


        }

        return paymentDialogs;

    }

    void deletePayment(Purchase purchase){
        this.purchasePaymentRepository.deletePurchasePaymentsByPurchase(purchase);
    }
}
