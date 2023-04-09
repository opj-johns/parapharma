package com.example.parapharma.domain.datamodels;

import com.example.parapharma.domain.OrderDetail;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.criterion.Order;

import java.util.List;

@Getter
@Setter
public class OrderDateUpdateDecision {
    private List<OrderDetail> orderDetails;
    private boolean updateOrderDate;
}
