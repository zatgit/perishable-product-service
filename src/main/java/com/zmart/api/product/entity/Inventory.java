package com.zmart.api.product.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.zmart.api.config.InventoryLoggingListener;
import com.zmart.api.product.validation.ValidAlpha;
import com.zmart.api.product.validation.ValidAlphaWithSpace;
import com.zmart.api.product.validation.ValidPositiveNumeral;
import com.zmart.api.product.validation.ValidQuality;
import com.zmart.api.product.validation.factory.InventoryConstraintViolationSetFactory;
import com.zmart.api.product.validation.group.InvPostCrudLogValidBuilderGroup;
import com.zmart.api.product.validation.group.InvPreCrudLogValidBuilderGroup;
import com.zmart.api.product.validation.group.ValidBuilderGroup;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.With;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

import static com.zmart.api.product.util.ProductUtility.throwConstraintViolationExOnViolations;

@With
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "Inventory")
@EntityListeners(InventoryLoggingListener.class)
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {

    private static final long serialVersionUID = 1323272533746093028L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "name", referencedColumnName = "productName", nullable = false, insertable = false, updatable = false)
    @JoinColumn(name = "code", referencedColumnName = "productCode", nullable = false, insertable = false, updatable = false)
    Product product;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @NotNull(groups = InvPostCrudLogValidBuilderGroup.class)
    private UUID uuid;

    @ValidAlphaWithSpace(groups = {Default.class,
            InvPreCrudLogValidBuilderGroup.class,
            InvPostCrudLogValidBuilderGroup.class})
    @Column(name = "name", nullable = false, length = 50)
    private String itemName;

    @ValidAlpha(groups = {Default.class,
            InvPreCrudLogValidBuilderGroup.class,
            InvPostCrudLogValidBuilderGroup.class})
    @Column(name = "code", nullable = false, length = 50)
    private String itemCode;

    @ValidPositiveNumeral
    @Column(name = "sellBy", nullable = false)
    private Integer sellBy;

    @ValidQuality
    @Column(name = "quality", nullable = false)
    private Integer quality;

    @UpdateTimestamp
    @Column(name = "stockDate", nullable = false)
    private ZonedDateTime stockDate;

    @CreationTimestamp
    @Column(name = "creationDate", nullable = false, updatable = false)
    private ZonedDateTime creationDate;

    //Only use for builders
    @Transient
    @Nullable
    private Class<? extends ValidBuilderGroup> builderValidationGroup;

    public static Inventory.InventoryBuilder builder() {
        return new ValidInventoryBuilder();
    }

    @Component
    private static class ValidInventoryBuilder extends Inventory.InventoryBuilder {

        private static InventoryConstraintViolationSetFactory factory;

        @Autowired
        public synchronized void injectFactory(InventoryConstraintViolationSetFactory factory) {
            ValidInventoryBuilder.factory = factory;
        }

        /**
         * Validation invoked by build() method.
         *
         * @return
         */
        public Inventory build() {
            final Inventory inventory = super.build();
            validateBuilder(inventory);
            return inventory;
        }

        private void validateBuilder(Inventory inventory) {
            final Set<ConstraintViolation<Inventory>> violations = factory.create(inventory);
            throwConstraintViolationExOnViolations(violations);
        }
    }
}
