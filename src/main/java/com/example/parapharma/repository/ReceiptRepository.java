package com.example.parapharma.repository;

import com.example.parapharma.domain.Receipt;
import com.example.parapharma.domain.ShopOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    Receipt findReceiptByShopOrder(ShopOrder order);
}
