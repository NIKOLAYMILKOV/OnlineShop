package com.shop.model.products.dtos;

import com.shop.model.location.LocationEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductRespDTO {
    private int id;
    private String name;
    private Integer quantity;
    private Double price;
    private LocationEntity location;
}
