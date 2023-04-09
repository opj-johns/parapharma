package com.example.parapharma.repository;

import com.example.parapharma.domain.OrderDetail;
import com.example.parapharma.domain.Product;
import com.example.parapharma.domain.ShopOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
   List<OrderDetail> getOrderDetailsByShopOrder(ShopOrder order);

   OrderDetail getOrderDetailByShopOrderAndAndProduct(ShopOrder order, Product product);

   void deleteOrderDetailsByShopOrder(ShopOrder order);
}
