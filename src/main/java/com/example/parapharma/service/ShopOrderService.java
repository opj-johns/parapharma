package com.example.parapharma.service;

import com.example.parapharma.domain.OrderDetail;
import com.example.parapharma.domain.OrderStatus;
import com.example.parapharma.domain.ShopOrder;
import com.example.parapharma.domain.datamodels.Command;
import com.example.parapharma.repository.ShopOrderRepository;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ShopOrderService {

    private ShopOrderRepository shopOrderRepository;
    OrderDetailService orderDetailService;
    PaymentService paymentService;


    public ShopOrderService(ShopOrderRepository shopOrderRepository,
                            OrderDetailService orderDetailService,
                            PaymentService paymentService) {
        this.shopOrderRepository = shopOrderRepository;
        this.orderDetailService = orderDetailService;
        this.paymentService = paymentService;
    }

    public ShopOrder saveOrder(ShopOrder shopOrder){

        shopOrder.setDate(new Date());

        return this.shopOrderRepository.save(shopOrder);
    }

    void updateOrderStatus(OrderStatus status){

        this.shopOrderRepository.findShopOrderByOrderStatus(status);
    }

    public List<Command> getCommands(){

        List<Command> commands = new ArrayList<>();

        List<ShopOrder> orders = this.shopOrderRepository.findAll();

        orders.forEach(order->{

            Command command = new Command();

            command.setDate(order.getDate());
            command.setOrderId(order.getId());
            command.setStatus(order.getOrderStatus().getStatus());
            command.setClientName(order.getClient().getFirstName() + " "+ order.getClient().getLastName());
            command.setEmployeeName(order.getEmployee().getFirstName()+" "+ order.getEmployee().getLastName());

            List<OrderDetail> orderDetails = this.orderDetailService.getOrderDetails(order);

            // use order to get the amount paid
            float amountPaid = this.paymentService.getAmountPaid(order);
            command.setAmountPaid(amountPaid);

            // total cost of order - amount paid gives amountToBePaid (credit)
            float orderTotalCost  =0;
            int numberOfProduct = 0;
            for(OrderDetail orderDetail: orderDetails){
                orderTotalCost += orderDetail.getQuantity() * orderDetail.getProduct().getPrice();
                numberOfProduct += orderDetail.getQuantity();
            }
            command.setAmountUnpaid(orderTotalCost - amountPaid);
            command.setTtc(orderTotalCost);
            command.setNumberOfProducts(numberOfProduct);

            commands.add(command);

        });

        return commands;
    }

    public ShopOrder getOrder(Long orderId){
        return this.shopOrderRepository.getReferenceById(orderId);
    }

    public void deleteOrder(ShopOrder order){
        // delete payments made with this order
        this.paymentService.deletePayment(order);
        // delete orderDetails recorded with this shop order
        this.orderDetailService.deleteOrderDetails(order);
        // delete shopOrder
        this.shopOrderRepository.delete(order);
    }

    public void updateOrderStatus(ShopOrder order){
        this.shopOrderRepository.save(order);
    }
}
