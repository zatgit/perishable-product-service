package com.zmart.api.product.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.Optional;

import static com.zmart.api.product.util.ProductConstants.PRODUCT_QUERY_PARAMS_DTO;

public record ProductGetAllRequest(
        @Valid
        @Nullable
        @JsonProperty(PRODUCT_QUERY_PARAMS_DTO)
        ProductQueryParamsDto productQueryParamsDto
) implements ProductRequest {

        @ConstructorBinding
        public ProductGetAllRequest(ProductQueryParamsDto productQueryParamsDto) {
                this.productQueryParamsDto = Optional.ofNullable(productQueryParamsDto)
                        .orElse(ProductQueryParamsDto.getProductQueryParamsDtoDefaults());
        }
}
