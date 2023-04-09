package com.example.parapharma.domain.datamodels;

import com.example.parapharma.domain.OrderDetail;
import com.example.parapharma.domain.PurchaseDetail;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PurchaseDateUpdateDecision {
    private List<PurchaseDetail> purchaseDetails;
    private boolean updatePurchaseDate;
}
