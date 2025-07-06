package com.shop.model.products.dtos;

import com.shop.model.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductCreateReqDTO {
    private String name;
    private Integer quantity;
    private Double price;
    private Location location;
}
