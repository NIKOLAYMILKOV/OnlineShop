package com.shop.model.location;

import com.shop.model.OrderLocationEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "locations")
public class LocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "location_x")
    private Integer x;
    @Column(name = "location_y")
    private Integer y;

    @OneToMany(mappedBy = "location")
    private List<OrderLocationEntity> orderLocations = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LocationEntity location = (LocationEntity) o;
        return Objects.equals(x, location.x) && Objects.equals(y, location.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
