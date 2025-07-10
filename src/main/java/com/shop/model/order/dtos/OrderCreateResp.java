package com.shop.model.order.dtos;

import com.shop.model.order.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class OrderCreateResp {
    private OrderStatus status;
    private String message;
}