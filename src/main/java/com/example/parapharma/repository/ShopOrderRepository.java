package com.example.parapharma.repository;

import com.example.parapharma.domain.OrderStatus;
import com.example.parapharma.domain.ShopOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopOrderRepository extends JpaRepository<ShopOrder, Long> {
    ShopOrder findShopOrderByOrderStatus(OrderStatus status);
}
