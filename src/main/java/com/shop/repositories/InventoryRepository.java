package com.shop.repositories;

import com.shop.model.location.LocationEntity;
import com.shop.model.products.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<ProductEntity, Integer> {
    boolean existsByName(String name);

    @Query(
        value = """
              SELECT quantity
              FROM products AS p
              WHERE p.name = :name
            """,
        nativeQuery = true
    )
    int findQuantityByName(String name);

    @Query(
        value = """
              SELECT COUNT(*)
              FROM products p
              JOIN locations l ON p.location_id = l.id
              WHERE l.location_x = :x AND l.location_y = :y
            """,
        nativeQuery = true
    )
    int countProductsAtLocation(@Param("x") Integer x, @Param("y") Integer y);

    @Modifying
    @Query(
        value = """
                UPDATE products AS p
                SET p.quantity = p.quantity - :quantity
                WHERE p.name = :name AND p.quantity >= :quantity
            """,
        nativeQuery = true
    )
    int reserveStock(@Param("name") String name, @Param("quantity") int quantity);

    default boolean existsByLocation(LocationEntity location) {
        Integer x = location.getX();
        Integer y = location.getY();
        return countProductsAtLocation(x, y) > 0;
    }

    @Query(
        value = """
              SELECT l.id, l.location_x, l.location_y
              FROM products p
              JOIN locations l ON p.location_id = l.id
              WHERE p.name = :name
            """,
        nativeQuery = true
    )
    LocationEntity findLocationByName(@Param("name") String name);
}
