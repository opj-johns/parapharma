package com.example.parapharma.domain.datamodels;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PurchaseTableData {
    private Date date;
    private Long purchaseId;
    private String status;
    private String supplierName;
    private int numberOfProducts;
    private float ttc;
    private float amountPaid;
    private float amountUnpaid;
}
