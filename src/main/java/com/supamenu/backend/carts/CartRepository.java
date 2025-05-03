package com.supamenu.backend.carts;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @EntityGraph(attributePaths = "items.menuItem")
    @Query("SELECT c FROM Cart c WHERE c.id = :cartId")
    Optional<Cart> findCartWithItems(@Param("cartId") Long cartId);
}
