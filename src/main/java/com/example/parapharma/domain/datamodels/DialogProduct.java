package com.example.parapharma.domain.datamodels;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;


@Getter
@Setter
@NoArgsConstructor
public class DialogProduct {
    private Long productId;
    private String productName;
    private String productUrl;
    private Long quantity;
    private float price;
}
