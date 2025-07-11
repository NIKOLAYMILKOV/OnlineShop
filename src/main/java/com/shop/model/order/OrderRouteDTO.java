package com.shop.model.order;

import com.shop.model.location.LocationDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRouteDTO {
    private Integer orderId;
    private OrderStatus status;
    private List<LocationDTO> visitedLocations = new ArrayList<>();
}
