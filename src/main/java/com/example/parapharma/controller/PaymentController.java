package com.example.parapharma.controller;

import com.example.parapharma.domain.Payment;
import com.example.parapharma.domain.PaymentType;
import com.example.parapharma.domain.ShopOrder;
import com.example.parapharma.domain.datamodels.PaymentDialogData;
import com.example.parapharma.domain.datamodels.UpdateOrderStatusDecision;
import com.example.parapharma.service.PaymentService;
import com.example.parapharma.service.ShopOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/payment")
public class PaymentController {

    private PaymentService paymentService;
    private ShopOrderService orderService;

    public PaymentController(PaymentService paymentService,
                             ShopOrderService orderService) {
        this.paymentService = paymentService;
        this.orderService = orderService;
    }

    @RequestMapping("/save")
    public ResponseEntity<Payment> makePayment(@RequestBody Payment newPayment){
        UpdateOrderStatusDecision decision = this.paymentService.savePayment(newPayment);
        if(decision.getIsUpdateOrderStatus()){
            this.orderService.updateOrderStatus(decision.getPayment().getShopOrder());
        }
        return ResponseEntity.ok(decision.getPayment());
    }

    @RequestMapping("/payment-dialogs")
    public ResponseEntity<List<PaymentDialogData> > fetchPaymentDialogData(@RequestBody ShopOrder order){
        List<PaymentDialogData> paymentDialogs = this.paymentService.getPaymentDialogData(order);
        return ResponseEntity.ok(paymentDialogs);
    }

    @RequestMapping("/types")
    public ResponseEntity<List<PaymentType>> fetchPaymentTypes(){
        List<PaymentType> paymentTypes = this.paymentService.getPaymentTypes();
        return ResponseEntity.ok(paymentTypes);
    }




}
