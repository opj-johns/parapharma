package com.example.parapharma.domain.datamodels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Command {
    private Date date;
    private Long orderId;
    private String status;
    private String clientName;
    private int numberOfProducts;
    private float ttc;
    private float amountPaid;
    private float amountUnpaid;
    private String employeeName;
}
