package com.zmart.api.product.controller;

import com.zmart.api.cache.aspect.CacheStatusHeader;
import com.zmart.api.docs.product.ProductRestControllerDocumentation;
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
import com.zmart.api.product.service.ProductInventoryService;
import com.zmart.api.product.validation.ValidAlpha;
import com.zmart.api.product.validation.ValidAlphaWithSpace;
import com.zmart.api.product.validation.ValidPositiveNumeral;
import com.zmart.api.product.validation.ValidQuality;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.zmart.api.docs.ApiDocsConstants.API_BASE_RESOURCE_PATH;

@Validated
@CrossOrigin(origins = {"${server.url.local}", "${server.url.production}"})
@RestController
@RequestMapping(
        path = API_BASE_RESOURCE_PATH + "product",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductRestController implements ProductRestControllerDocumentation {

    private final ProductInventoryService productInventoryService;

    public ProductRestController(
            final ProductInventoryService productInventoryService) {
        this.productInventoryService = productInventoryService;
    }

    @GetMapping("all")
    @CacheStatusHeader
    public ResponseEntity<ProductAllProdsResponse> getAllProducts(
            @Valid @ParameterObject ProductQueryParamsDto request) {
        ProductAllProdsResponse response = productInventoryService.getAllProducts(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("id")
    @CacheStatusHeader
    public ResponseEntity<ProductByIdResponse> getProductById(
            @NotNull @RequestParam final UUID uuid,
            @ValidPositiveNumeral @RequestParam(defaultValue = "0") final Integer dayOffset) {
        final ProductByIdResponse response = productInventoryService.getProductById(uuid, dayOffset);
        return ResponseEntity.ok(response);
    }

    @GetMapping("item-name")
    @CacheStatusHeader
    public ResponseEntity<ProductByItemNameResponse> getProductByItemName(
            @ValidAlphaWithSpace @RequestParam final String itemName,
            @Valid @ParameterObject final ProductQueryParamsDto request) {
        final ProductByItemNameResponse response = productInventoryService.getProductByItemName(itemName, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("item-code")
    @CacheStatusHeader
    public ResponseEntity<ProductByItemCodeResponse> getProductByItemCode(
        @ValidAlpha @RequestParam final String itemCode,
        @Valid @ParameterObject final ProductQueryParamsDto request) {
        final ProductByItemCodeResponse response = productInventoryService.getProductByItemCode(itemCode, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("quality")
    @CacheStatusHeader
    public ResponseEntity<ProductsByQualityResponse> getProductsByQuality(
            @ValidQuality @RequestParam final Integer quality,
            @Valid @ParameterObject final ProductQueryParamsDto request) {
        final ProductsByQualityResponse response =
                productInventoryService.getProductsByQuality(quality, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductCreateResponse> createProducts(
            @RequestBody @Valid final ProductCreateRequest request) {
        final ProductCreateResponse response = productInventoryService.createProducts(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDeleteResponse> deleteProductsById(
            @RequestBody @Valid final ProductDeleteRequest request) {
        final ProductDeleteResponse response = productInventoryService.deleteProductsById(request);
        return ResponseEntity.ok(response);
    }
}
