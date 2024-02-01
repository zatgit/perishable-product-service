package com.zmart.api.product.util;

import com.zmart.api.product.dto.ProductDto;
import com.zmart.api.product.entity.Inventory;
import com.zmart.api.product.entity.Product;
import com.zmart.api.product.validation.factory.ProductUtilsConstraintViolationSetFactory;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@UtilityClass
public final class ProductUtility {

    public static String getClassAndMethodFromFqdn(final String fQDN) {
        validateStaticConstraints(new StaticPropertyValidationMap().withFQDN(fQDN), "fQDN");
        int secondToLastDotIndex = fQDN.lastIndexOf(".", fQDN.lastIndexOf(".") - 1);
        return fQDN.substring(secondToLastDotIndex + 1);
    }

    protected static void validateStaticConstraints(StaticPropertyValidationMap map, String... propertyName) {
        Set<ConstraintViolation<StaticPropertyValidationMap>> violations =
                Arrays.stream(propertyName)
                        .map(p ->
                                new ProductUtilsConstraintViolationSetFactory()
                                        .create(map, propertyName[0]))
                        .toList()
                        .get(0);
        throwConstraintViolationExOnViolations(violations);
    }

    public static void throwConstraintViolationExOnViolations(Set<? extends ConstraintViolation<?>> violations) {
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    public static String replaceBrackets(String bracketedString) {
        validateStaticConstraints(new StaticPropertyValidationMap()
                .withBracketedString(bracketedString), "bracketedString");
        return bracketedString.replace("[", ProductConstants.EMPTY_STRING).replace("]", ProductConstants.EMPTY_STRING);
    }

    public static Integer getInventoryCount(final List<Product> productList) {
        validateStaticConstraints(new StaticPropertyValidationMap()
                .withProductList(productList), ProductConstants.PRODUCT_LIST);
        return Math.toIntExact(productList.stream()
                .flatMap(p -> p.getInventoryList().stream())
                .count());
    }

    public static boolean isObjectEmpty(Object objectList) {
        validateStaticConstraints(new StaticPropertyValidationMap()
                .withObjectList(objectList), "objectList");
        return objectList.equals(Collections.emptyList());
    }

    @Nullable
    public static String trimQueryParam(@Nullable String queryParam) {
        return queryParam != null ? queryParam.trim() : null;
    }

    public static List<Product> buildProductDummyData(
            ProductDummyData[] prodData,
            InventoryDummyData[] invData) {
        validateStaticConstraints(new StaticPropertyValidationMap()
                .withProdData(prodData).withInvData(invData), "prodData");
        return Stream.of(prodData)
                .map(p -> Product.builder()
                        .itemName(p.getItemName())
                        .itemCode(p.getItemCode())
                        .qualityOperation(p.getQualityOperation())
                        .inventoryList(
                                Stream.of(invData[p.getOrdinal()])
                                        .map(i -> Inventory.builder()
                                                .itemName(i.getItemName())
                                                .itemCode(i.getItemCode())
                                                .sellBy(i.getSellBy())
                                                .quality(i.getQuality())
                                                .build())
                                        .toList())
                        .build())
                .toList();
    }

    @NotNull
    public static HttpStatus getSingleResourceResponseStatus(
            @NotNull final ProductDto productDto) {
        return productDto.itemName() != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
    }
}
