package com.example.parapharma.domain.datamodels;

import com.example.parapharma.domain.Payment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderStatusDecision {
    Payment payment;
    Boolean isUpdateOrderStatus;
    Long statusId;
}
