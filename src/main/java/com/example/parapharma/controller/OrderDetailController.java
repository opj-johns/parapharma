package com.example.parapharma.controller;
import com.example.parapharma.domain.OrderDetail;
import com.example.parapharma.domain.ShopOrder;
import com.example.parapharma.domain.datamodels.DialogProduct;
import com.example.parapharma.domain.datamodels.OrderDateUpdateDecision;
import com.example.parapharma.service.OrderDetailService;
import com.example.parapharma.service.ShopOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@Controller
@CrossOrigin(origins = {"http://localhost:4200", "https://parapharma-82f7f.web.app"})
@RequestMapping("/api/order-detail")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private ShopOrderService shopOrderService;

    @RequestMapping("/save")
    public ResponseEntity<List<OrderDetail>> saveOrderDetail(@RequestBody List<OrderDetail> orderDetails){
        // save order first. Then use it to save order detail
        ShopOrder order = this.shopOrderService.saveOrder(orderDetails.get(0).getShopOrder());

        List<OrderDetail> resp = this.orderDetailService.saveOrder(orderDetails, order);
        return ResponseEntity.ok(resp);
    }
    @RequestMapping("/by-order")
    public ResponseEntity<List<OrderDetail>> fetchOrderDetails(@RequestBody ShopOrder shopOrder){
        List<OrderDetail> orderDetails = this.orderDetailService.getOrderDetails(shopOrder);
        return ResponseEntity.ok(orderDetails);
    }

    @RequestMapping("/dialog-products")
    public ResponseEntity<List<DialogProduct>> fetchDialogProducts(@RequestBody ShopOrder order){
        List<DialogProduct> dialogProducts = this.orderDetailService.getDialogProduct(order);
        return ResponseEntity.ok(dialogProducts);
    }

    @RequestMapping("/update")
    public ResponseEntity<List<OrderDetail>> updateOrderDetails(@RequestBody List<OrderDetail> orderDetails){
        OrderDateUpdateDecision decision = this.orderDetailService.updateOrderDetails(orderDetails);


        if(decision.isUpdateOrderDate()){

            decision.getOrderDetails()
                    .get(0)
                    .getShopOrder()
                    .setDate(new Date());

            this.shopOrderService.saveOrder(
                    decision.getOrderDetails()
                            .get(0).getShopOrder()
            );
        }

        return ResponseEntity.ok(decision.getOrderDetails());
    }

}
