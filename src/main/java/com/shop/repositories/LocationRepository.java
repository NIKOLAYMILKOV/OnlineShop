package com.shop.repositories;

import com.shop.model.location.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, Integer> {
    @Query(value = """
            SELECT * FROM locations l
            WHERE l.location_x = :x AND l.location_y = :y
        """,
        nativeQuery = true)
    Optional<LocationEntity> findByXAndY(@Param("x") Integer x, @Param("y") Integer y);

    boolean existsByXAndY(Integer x, Integer y);
}
