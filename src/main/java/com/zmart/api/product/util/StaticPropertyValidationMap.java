package com.zmart.api.product.util;

import com.zmart.api.product.entity.Product;
import com.zmart.api.product.validation.ValidFullyQualifiedDomainName;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.List;

/**
 * Used to validate static methods' parameters of {@link ProductConstants}
 * <br>Validation of this object is triggered via the Bean Validation API in:
 * {@link ProductUtility#validateStaticConstraints(StaticPropertyValidationMap, String...)}
 * <br>Constraints may only be applied to instance methods, i.e. declaring constraints on static methods is not supported.
 * <br>Utility classes cannot be instantiated, necessitating this object.
 * */
@With
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StaticPropertyValidationMap {
    @NotNull
    @ValidFullyQualifiedDomainName
    private String fQDN;

    @NotNull
    private String bracketedString;

    @NotNull
    private List<@Valid @NotNull Product> productList;

    @NotNull
    private Object objectList;

    @NotNull
    private ProductDummyData[] prodData;

    @NotNull
    private InventoryDummyData[] invData;
}
