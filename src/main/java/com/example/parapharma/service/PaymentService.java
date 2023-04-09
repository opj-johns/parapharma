package com.example.parapharma.service;

import com.example.parapharma.domain.*;
import com.example.parapharma.domain.datamodels.PaymentDialogData;
import com.example.parapharma.domain.datamodels.UpdateOrderStatusDecision;
import com.example.parapharma.repository.PaymentRepository;
import com.example.parapharma.repository.PaymentTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PaymentService {
    private PaymentRepository paymentRepository;
    private PaymentTypeRepository paymentTypeRepository;
    private OrderDetailService orderDetailService;
    private ProductService productService;

    public PaymentService(PaymentRepository paymentRepository,
                          PaymentTypeRepository paymentTypeRepository,
                          OrderDetailService orderDetailService,
                          ProductService productService) {
        this.paymentRepository = paymentRepository;
        this.paymentTypeRepository = paymentTypeRepository;
        this.orderDetailService = orderDetailService;
        this.productService = productService;
    }



    public UpdateOrderStatusDecision savePayment(Payment payment){
        UpdateOrderStatusDecision decision = new UpdateOrderStatusDecision();
        // if this is the first payment, alter order state to payment
        List<Payment> payments = this.paymentRepository.findAllByShopOrder(payment.getShopOrder());
        if(payments==null){
            decision.setIsUpdateOrderStatus(true);
        }else{
            decision.setIsUpdateOrderStatus(false);
        }

        payment.setPaymentDate(new Date());

        Payment payment1 = this.paymentRepository.save(payment);
        decision.setPayment(payment1);

        // if total amount paid is equal to total order cost, reduce the number of products in stock
        // of each of the products in the order by the quantity orderd
        float amountPaid = getAmountPaid(payment1.getShopOrder());
        float orderCost = this.getTotalOrderCost(payment1.getShopOrder());
        if(orderCost == amountPaid){
            List<OrderDetail> details = this.orderDetailService.getOrderDetails(payment1.getShopOrder());
            this.productService.updateQuantityInStock(details);
        }

        return decision;
    }

    public float getTotalOrderCost(ShopOrder order){
        List<OrderDetail> details = this.orderDetailService.getOrderDetails(order);
        float totalOrderCost = 0;

        for(OrderDetail detail: details){
            totalOrderCost += detail.getProduct().getPrice() * detail.getQuantity();
        }
        return totalOrderCost;
    }

    public float getAmountPaid(ShopOrder order){

       List<Payment> payments = this.paymentRepository.findAllByShopOrder(order);
        float total =0;

       if(payments.size()!=0 ){
           for(Payment payment: payments){
               total+=payment.getAmountPaid();
           }
       }

       return total;
    }

    public List<PaymentDialogData> getPaymentDialogData(ShopOrder order){

        List<Payment> payments = this.paymentRepository.findAllByShopOrder(order);

        List<PaymentDialogData> paymentDialogs = new ArrayList<>();

        for(Payment payment : payments){
            PaymentDialogData paymentDialog = new PaymentDialogData();

            paymentDialog.setTypeOfPayment(payment.getPaymentType().getType());
            paymentDialog.setAmountPaid(payment.getAmountPaid());

            String stringDate = payment.getPaymentDate().toString();

            List<String> parts = List.of(stringDate.split(" "));

            paymentDialog.setDate(parts.get(0));
            parts = List.of(parts.get(1).split(":"));
            paymentDialog.setTime(parts.get(0)+":"+parts.get(1));

            paymentDialogs.add(paymentDialog);


        }

        return paymentDialogs;

    }

    public List<PaymentType> getPaymentTypes(){
        return this.paymentTypeRepository.findAll();
    }

    void deletePayment(ShopOrder order){
        this.paymentRepository.deletePaymentsByShopOrder(order);
    }


}
