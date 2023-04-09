package com.example.parapharma.repository;

import com.example.parapharma.domain.ShopOrder;
import com.example.parapharma.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findAllByShopOrder(ShopOrder order);

    void deletePaymentsByShopOrder(ShopOrder order);

}
