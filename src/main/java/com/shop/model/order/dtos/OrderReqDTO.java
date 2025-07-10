package com.shop.model.order.dtos;

import com.shop.model.order.ItemEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderReqDTO {
    private List<ItemEntity> items;
}
