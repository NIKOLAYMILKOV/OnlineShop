package com.shop.model;

import com.shop.model.location.LocationEntity;
import com.shop.model.order.OrderEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders_have_locations")
public class OrderLocationEntity {
    @EmbeddedId
    private OrderLocationId id;

    @ManyToOne
    @MapsId("orderId")
    private OrderEntity order;

    @ManyToOne
    @MapsId("locationId")
    private LocationEntity location;

    @Column(name = "step_sequence_number")
    private Integer stepSequenceNumber;
}
