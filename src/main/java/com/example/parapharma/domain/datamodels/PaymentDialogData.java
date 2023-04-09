package com.example.parapharma.domain.datamodels;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PaymentDialogData {
    private String time;
    private String date;
    private String typeOfPayment;
    private float amountPaid;
}
