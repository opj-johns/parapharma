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
import java.util.Optional;

@Service
@Transactional
public class PurchasePaymentService {

    private PurchasePaymentRepository purchasePaymentRepository;
    private PaymentTypeRepository paymentTypeRepository;
    private PurchaseDetailService purchaseDetailService;

    public PurchasePaymentService(PurchasePaymentRepository purchasePaymentRepository,
                                  PaymentTypeRepository paymentTypeRepository,
                                  PurchaseDetailService purchaseDetailService
                                  ) {
        this.purchasePaymentRepository = purchasePaymentRepository;
        this.paymentTypeRepository = paymentTypeRepository;
        this.purchaseDetailService = purchaseDetailService;
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
        PaymentType paymentType = this.paymentTypeRepository.getReferenceById(
                payment.getPaymentType().getId()
        );
        if(paymentType!=null){
            payment.setPaymentType(paymentType);
        }
        payment.getPurchase().setStatus(
                getPurchaseStatus(payment)
        );
        payment.setDate(new Date());
        // If status is completed, we can now safely
        // add the purchased products to stock by increasing
        // each product qtyInStock property accordingly
        return this.purchasePaymentRepository.save(payment);
    }



    PurchaseStatus getPurchaseStatus(PurchasePayment payment){
        List<PurchaseDetail> purchaseDetails = this.purchaseDetailService.getAll(
                payment.getPurchase()
        );

        Double totalPurchaseAmount = purchaseDetails
                .stream()
                .mapToDouble(purchaseDetail -> purchaseDetail.getPurchasePrice() * purchaseDetail.getQuantity())
                .sum();

        List<PurchasePayment> purchasePayments =
                this.purchasePaymentRepository.
                        findPurchasePaymentsByPurchase(payment.getPurchase());

        // make sure purchasePayments is never null
        purchasePayments = Optional.ofNullable(purchasePayments).orElseGet(()->List.of());

        Double totalAmountPaid = purchasePayments
                .stream()
                .mapToDouble(pPayment-> pPayment.getAmount_paid())
                .sum();

        if(totalAmountPaid==0.0){
            return new PurchaseStatus(1L,"Initié");
        }else {
            if(totalAmountPaid>0.0 && totalAmountPaid<totalPurchaseAmount){
                return new PurchaseStatus(2L,"Paiement");
            }
        }

        return new PurchaseStatus(3L,"Complété");
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
