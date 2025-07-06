package com.shop.repositories;

import com.shop.model.Location;
import com.shop.model.products.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<ProductEntity, Integer> {
    boolean existsByName(String name);

    boolean existsByLocation(Location location);
}
