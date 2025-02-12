package com.zmart.api.product.service;

import com.zmart.api.product.dto.request.ProductCreateRequest;
import com.zmart.api.product.dto.request.ProductDeleteRequest;
import com.zmart.api.product.dto.request.ProductQueryParamsDto;
import com.zmart.api.product.dto.response.ProductAllProdsResponse;
import com.zmart.api.product.dto.response.ProductByIdResponse;
import com.zmart.api.product.dto.response.ProductByItemCodeResponse;
import com.zmart.api.product.dto.response.ProductByItemNameResponse;
import com.zmart.api.product.dto.response.ProductCreateResponse;
import com.zmart.api.product.dto.response.ProductDeleteResponse;
import com.zmart.api.product.dto.response.ProductsByQualityResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface ProductInventoryService {

    /**
     * Gets all products.
     *
     * @param request query params
     * @return all products
     */
    ProductAllProdsResponse getAllProducts(
            @NotNull ProductQueryParamsDto request);

    /**
     * Gets product by id.
     * @param uuid
     * @param dayOffset
     * @param request query params
     * @return products by id
     */
    ProductByIdResponse getProductById(
            @NotNull UUID uuid,
            @NotNull Integer dayOffset);

    /**
     * Gets product by quality.
     * @param quality
     * @param request query params
     * @return products by quality
     */
    ProductsByQualityResponse getProductsByQuality(
            @NotNull Integer quality,
            @NotNull ProductQueryParamsDto request);

    /**
     * Gets product by item name.
     * @param itemName
     * @param request query params
     * @return product by item name
     */
    ProductByItemNameResponse getProductByItemName(
            @NotNull String itemName,
            @NotNull ProductQueryParamsDto request);

    /**
     * Gets product by item code.
     * @param itemCode
     * @param request query params
     * @return product by item code
     */
    ProductByItemCodeResponse getProductByItemCode(
            @NotNull String itemCode,
            @NotNull ProductQueryParamsDto request);

    /**
     * Create products.
     *
     * @param request the product creation request
     * @return product creation response
     */
    ProductCreateResponse createProducts(
            @NotNull ProductCreateRequest request);

    /**
     * Delete products by id.
     *
     * @param request the product deletion request
     * @return product deletion response
     */
    ProductDeleteResponse deleteProductsById(
            @NotNull ProductDeleteRequest request);
}
