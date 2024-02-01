package com.zmart.api.product.service;

import com.zmart.api.product.dto.request.ProductAllProdsRequest;
import com.zmart.api.product.dto.request.ProductByIdRequest;
import com.zmart.api.product.dto.request.ProductByItemCodeRequest;
import com.zmart.api.product.dto.request.ProductByItemNameRequest;
import com.zmart.api.product.dto.request.ProductCreationRequest;
import com.zmart.api.product.dto.request.ProductDeletionRequest;
import com.zmart.api.product.dto.request.ProductsByQualityRequest;
import com.zmart.api.product.dto.response.ProductAllProdsResponse;
import com.zmart.api.product.dto.response.ProductByIdResponse;
import com.zmart.api.product.dto.response.ProductByItemCodeResponse;
import com.zmart.api.product.dto.response.ProductByItemNameResponse;
import com.zmart.api.product.dto.response.ProductCreationResponse;
import com.zmart.api.product.dto.response.ProductDeletionResponse;
import com.zmart.api.product.dto.response.ProductsByQualityResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

@Service
public interface ProductInventoryService {

    /**
     * Gets all products.
     *
     * @param request the request
     * @return all products
     */
    ProductAllProdsResponse getAllProducts(
            @NotNull ProductAllProdsRequest request);

    /**
     * Gets product by id.
     *
     * @param request the id request
     * @return products by id
     */
    ProductByIdResponse getProductById(
            @NotNull ProductByIdRequest request);

    /**
     * Gets product by quality.
     *
     * @param request the quality request
     * @return products by quality
     */
    ProductsByQualityResponse getProductsByQuality(
            @NotNull ProductsByQualityRequest request);

    /**
     * Gets product by item name.
     *
     * @param request the item name request
     * @return product by item name
     */
    ProductByItemNameResponse getProductByItemName(
            @NotNull ProductByItemNameRequest request);

    /**
     * Gets product by item code.
     *
     * @param request the item code request
     * @return product by item code
     */
    ProductByItemCodeResponse getProductByItemCode(
            @NotNull ProductByItemCodeRequest request);

    /**
     * Create products.
     *
     * @param request the product creation request
     * @return product creation response
     */
    ProductCreationResponse createProducts(
            @NotNull ProductCreationRequest request);

    /**
     * Delete products by id.
     *
     * @param request the product deletion request
     * @return product deletion response
     */
    ProductDeletionResponse deleteProductsById(
            @NotNull ProductDeletionRequest request);
}
