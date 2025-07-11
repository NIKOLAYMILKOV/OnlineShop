package com.shop.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class OrderLocationId implements Serializable {
    private Integer orderId;
    private Integer locationId;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderLocationId that = (OrderLocationId) o;
        return Objects.equals(orderId, that.orderId) && Objects.equals(locationId, that.locationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, locationId);
    }
}
