package com.zmart.api.logging.listener;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zmart.api.product.entity.Inventory;
import com.zmart.api.product.validation.group.InvPostCrudLogValidBuilderGroup;
import com.zmart.api.product.validation.group.InvPreCrudLogValidBuilderGroup;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class InventoryLoggingListener {

    private static final String INVENTORY = "Inventory";

    ObjectMapper objectMapper;

    public InventoryLoggingListener(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @PrePersist
    public void logPrePersistInventory(final Inventory inventory) {
        log.trace("[INSERTING] {} {}", INVENTORY, getPreCrudInventoryLogObject(inventory));
    }

    @PostPersist
    public void logPostPersistInventory(final Inventory inventory) {
        log.debug("[INSERTED] {} {}", INVENTORY, getPostCrudInventoryLogObject(inventory));
    }

    @PreUpdate
    public void logPreUpdateInventory(final Inventory inventory) {
        log.trace("[UPDATING] {} {}", INVENTORY, getPreCrudInventoryLogObject(inventory));
    }

    @PostUpdate
    public void logPostUpdateInventory(final Inventory inventory) {
        log.debug("[UPDATED] {} {}", INVENTORY, getPostCrudInventoryLogObject(inventory));
    }

    @PreRemove
    public void logPreRemoveInventory(final Inventory inventory) {
        log.trace("[REMOVING] {} {}", INVENTORY, getPreCrudInventoryLogObject(inventory));
    }

    @PostRemove
    public void logPostRemoveInventory(final Inventory inventory) {
        log.debug("[REMOVED] {} {}", INVENTORY, getPostCrudInventoryLogObject(inventory));
    }

    @SneakyThrows
    private String getPreCrudInventoryLogObject(final Inventory inventory) {
        return this.objectMapper.writeValueAsString(
                Inventory.builder()
                        .itemName(inventory.getItemName())
                        .itemCode(inventory.getItemCode())
                        .builderValidationGroup(InvPreCrudLogValidBuilderGroup.class)
                        .build());
    }

    @SneakyThrows
    private String getPostCrudInventoryLogObject(final Inventory inventory) {
        return objectMapper.writeValueAsString(
                Inventory.builder()
                        .itemName(inventory.getItemName())
                        .itemCode(inventory.getItemCode())
                        .uuid(inventory.getUuid())
                        .builderValidationGroup(InvPostCrudLogValidBuilderGroup.class)
                        .build());
    }
}
