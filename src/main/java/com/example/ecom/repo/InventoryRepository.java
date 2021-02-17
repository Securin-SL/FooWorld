package com.example.ecom.repo;

import com.example.ecom.model.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Query("select i from Inventory i where i.product.id = :productId")
    Optional<Inventory> getInventoryByProductId(@Param("productId") Long productId);

    @Query("select i from Inventory i where i.product.id in :productIds")
    List<Inventory> getInventoriesByProductId(@Param("productIds") List<Long> productIds);
}
