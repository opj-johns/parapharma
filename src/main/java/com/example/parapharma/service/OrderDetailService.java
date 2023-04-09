package com.example.parapharma.service;

import com.example.parapharma.domain.OrderDetail;
import com.example.parapharma.domain.ShopOrder;
import com.example.parapharma.domain.datamodels.DialogProduct;
import com.example.parapharma.domain.datamodels.OrderDateUpdateDecision;
import com.example.parapharma.repository.OrderDetailRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailService {

    private OrderDetailRepository orderDetailRepository;

    public OrderDetailService(OrderDetailRepository orderDetailRepository){
        this.orderDetailRepository = orderDetailRepository ;
    }

    public List<OrderDetail> saveOrder(List<OrderDetail> orderDetails, ShopOrder order){

        List<OrderDetail> savedOrderDetails = new ArrayList<>();
        //
        if(order!=null){
            List<OrderDetail> orderDetailList = new ArrayList<>();
            orderDetails.stream().forEach(orderDetail -> {
                orderDetail.setShopOrder(order);
                orderDetailList.add(orderDetail);
            });

          savedOrderDetails = this.orderDetailRepository.saveAll(orderDetailList);
        }
        return savedOrderDetails;
    }

    public List<OrderDetail> getOrderDetails(ShopOrder order){
        return this.orderDetailRepository.getOrderDetailsByShopOrder(order);
    }

    public List<DialogProduct> getDialogProduct(ShopOrder order){
        List<OrderDetail> orderDetails = this.orderDetailRepository.getOrderDetailsByShopOrder(order);

        List<DialogProduct> dialogProducts = new ArrayList<>();

        for(OrderDetail orderDetail : orderDetails){
            DialogProduct dialogProduct = new DialogProduct();
            dialogProduct.setProductId(orderDetail.getProduct().getId());
            dialogProduct.setProductName(orderDetail.getProduct().getName());
            dialogProduct.setQuantity(orderDetail.getQuantity());
            dialogProduct.setPrice(orderDetail.getProduct().getPrice());
            dialogProduct.setProductUrl(orderDetail.getProduct().getImageUrl());
            dialogProducts.add(dialogProduct);
        }

        return dialogProducts;
    }


    public OrderDateUpdateDecision updateOrderDetails(List<OrderDetail> orderDetails){

        // get all order details with the order of the incoming orderDetails
        // they all have one order (with one orderId)
        List<OrderDetail> existingOrderDetails = this.getOrderDetails(orderDetails.get(0).getShopOrder());

        // check the products in "orderDetails" and "existingOrderDetails"

        // if they are the same, increase the "quantity" variable
        // of "existingOrderDetails" and remove it from "orderDetail"

        // else

//        List<Long> oldProdIds = new ArrayList<>();
        boolean updateOrderDate = false;


        for(int inIndex=0; inIndex<orderDetails.size(); inIndex++){
            for(int oldIndex=0; oldIndex< existingOrderDetails.size(); oldIndex++){
                if(existingOrderDetails.get(oldIndex).getProduct().getId()
                        ==
                   orderDetails.get(inIndex).getProduct().getId()){
                    // set id for incoming
                   orderDetails.get(inIndex).setId(
                           existingOrderDetails.get(oldIndex).getId()
                   );
                   // increase quantity of incoming
                    orderDetails.get(inIndex).setQuantity(
                            orderDetails.get(inIndex).getQuantity()+
                            existingOrderDetails.get(oldIndex).getQuantity()
                    );
                    // set updateOrderID
                    updateOrderDate = true;
                }
            }
        }

        OrderDateUpdateDecision orderDateUpdateDecision = new OrderDateUpdateDecision();
        orderDateUpdateDecision.setOrderDetails(
                this.orderDetailRepository.saveAll(orderDetails)
        );
        orderDateUpdateDecision.setUpdateOrderDate(updateOrderDate);

        return orderDateUpdateDecision;
    }


    void deleteOrderDetails(ShopOrder order){
        this.orderDetailRepository.deleteOrderDetailsByShopOrder(order);
    }


}
