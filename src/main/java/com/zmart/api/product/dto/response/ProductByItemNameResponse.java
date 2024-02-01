package com.zmart.api.product.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zmart.api.product.dto.ProductDto;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import static com.zmart.api.product.util.ProductConstants.PRODUCT;

@Builder
public record ProductByItemNameResponse(
        @NotNull
        @JsonProperty(PRODUCT)
        ProductDto productDto
) implements ProductResponse {
}
