package com.zmart.api.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zmart.api.product.entity.Inventory;
import com.zmart.api.product.entity.Product;
import com.zmart.api.product.validation.group.ProdLogValidBuilderGroup;
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
public class ProductLoggingListener {

    private static final String PRODUCT = "Product";

    ObjectMapper objectMapper;

    public ProductLoggingListener(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @PrePersist
    public void logPrePersistInventory(final Product product) {
        log.trace("[INSERTING] {} {}", PRODUCT, getProductLogObject(product));
    }

    @PostPersist
    public void logPostPersistInventory(final Product product) {
        log.debug("[INSERTED] {} {}", PRODUCT, getProductLogObject(product));
    }

    @PreUpdate
    public void logPreUpdateInventory(final Product product) {
        log.trace("[UPDATING] {} {}", PRODUCT, getProductLogObject(product));
    }

    @PostUpdate
    public void logPostUpdateInventory(final Product product) {
        log.debug("[UPDATED] {} {}", PRODUCT, getProductLogObject(product));
    }

    @PreRemove
    public void logPreRemoveInventory(final Product product) {
        log.trace("[REMOVING] {} {}", PRODUCT, getProductLogObject(product));
    }

    @PostRemove
    public void logPostRemoveInventory(final Product product) {
        log.debug("[REMOVED] {} {}", PRODUCT, getProductLogObject(product));
    }

    @SneakyThrows
    private String getProductLogObject(final Product product) {
        return objectMapper.writeValueAsString(
                Inventory.builder()
                        .itemName(product.getItemName())
                        .itemCode(product.getItemCode())
                        .builderValidationGroup(ProdLogValidBuilderGroup.class)
                        .build());
    }
}