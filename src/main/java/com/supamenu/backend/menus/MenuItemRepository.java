package com.supamenu.backend.menus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long>, JpaSpecificationExecutor<MenuItem> {



}
