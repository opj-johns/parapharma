package com.example.parapharma.controller;

import com.example.parapharma.domain.ShopOrder;
import com.example.parapharma.domain.datamodels.Command;
import com.example.parapharma.domain.datamodels.DialogProduct;
import com.example.parapharma.service.ShopOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("api/order")
public class ShopOrderController {

    private ShopOrderService shopOrderService;

    public ShopOrderController(ShopOrderService shopOrderService) {
        this.shopOrderService = shopOrderService;
    }

    @RequestMapping("/commands")
    public ResponseEntity<List<Command>> fetchCommands(){
        List<Command> commands = this.shopOrderService.getCommands();
        return ResponseEntity.ok(commands);
    }

    @RequestMapping("/{id}")
    public ResponseEntity<ShopOrder> fetchOrder(@PathVariable("id") Long orderId){
        ShopOrder order = this.shopOrderService.getOrder(orderId);
        return ResponseEntity.ok(order);
    }

    @RequestMapping("/delete")
    public ResponseEntity<?> deleteShopOrder(@RequestBody ShopOrder order){
        this.shopOrderService.deleteOrder(order);
        return new ResponseEntity<>(HttpStatus.OK);
    }




}
