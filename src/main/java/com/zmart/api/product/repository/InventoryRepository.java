package com.zmart.api.product.repository;

import com.zmart.api.product.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional
@Repository
public interface InventoryRepository extends JpaRepository<Inventory, UUID> {
    List<Inventory> deleteAllByUuidIn(@NonNull List<UUID> uuid);
}
