package com.shop.repositories;

import com.shop.model.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, Integer> {
    Optional<LocationEntity> findByXAndY(Integer x, Integer y);

    boolean existsByXAndY(Integer x, Integer y);
}
