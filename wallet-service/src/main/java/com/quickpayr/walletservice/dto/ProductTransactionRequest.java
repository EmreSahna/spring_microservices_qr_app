package com.quickpayr.walletservice.dto;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Embeddable
public class ProductTransactionRequest {
    private Long id;
    private Integer quantity;
    private BigDecimal price;
}
