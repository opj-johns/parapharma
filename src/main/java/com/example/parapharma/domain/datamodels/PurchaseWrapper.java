package com.example.parapharma.domain.datamodels;

import com.example.parapharma.domain.Purchase;
import com.example.parapharma.domain.PurchaseDetail;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PurchaseWrapper {
    private Purchase purchase;
    private List<PurchaseDetail> purchaseDetails;
}
