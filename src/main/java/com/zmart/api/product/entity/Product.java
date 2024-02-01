package com.zmart.api.product.entity;

import com.zmart.api.config.ProductLoggingListener;
import com.zmart.api.product.validation.ValidAlpha;
import com.zmart.api.product.validation.ValidAlphaWithSpace;
import com.zmart.api.product.validation.ValidNullableQuality;
import com.zmart.api.product.validation.ValidQualityOperation;
import com.zmart.api.product.validation.factory.ProductConstraintViolationSetFactory;
import com.zmart.api.product.validation.group.ValidBuilderGroup;
import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.With;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

import static com.zmart.api.product.util.ProductConstants.PRODUCT;
import static com.zmart.api.product.util.ProductUtility.throwConstraintViolationExOnViolations;


@With
@Getter
@Setter
@EqualsAndHashCode
@Entity
@IdClass(ProductId.class)
@Table(name = "Product")
@EntityListeners(ProductLoggingListener.class)
@RequiredArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Product {

    @NotNull
    @OneToMany(mappedBy = PRODUCT, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<@NotNull Inventory> inventoryList;

    @Id
    @ValidAlphaWithSpace
    @Column(name = "productName", nullable = false, length = 50)
    private String itemName;

    @Id
    @ValidAlpha
    @Column(name = "productCode", nullable = false, length = 50)
    private String itemCode;

    @ValidQualityOperation
    @Column(name = "qualityOperation", nullable = false)
    private Integer qualityOperation;

    @Transient
    @ValidNullableQuality
    private Integer quality;

    //Only use for builders
    @Transient
    @Nullable
    private Class<? extends ValidBuilderGroup> builderValidationGroup;

    public static Product.ProductBuilder builder() {
        return new Product.ValidProductBuilder();
    }

    @Component
    private static class ValidProductBuilder extends Product.ProductBuilder {

        private static ProductConstraintViolationSetFactory factory;

        @Autowired
        public synchronized void injectFactory(ProductConstraintViolationSetFactory factory) {
            Product.ValidProductBuilder.factory = factory;
        }

        /**
         * Validation invoked by build() method.
         *
         * @return
         */
        public Product build() {
            final Product product = super.build();
            validateBuilder(product);
            return product;
        }

        private void validateBuilder(Product product) {
            final Set<ConstraintViolation<Product>> violations = factory.create(product);
            throwConstraintViolationExOnViolations(violations);
        }
    }
}
