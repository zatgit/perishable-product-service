package com.zmart.api.product.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.zmart.api.product.dto.ProductDto;
import com.zmart.api.product.validation.ValidUniqueCollection;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

import java.util.List;

import static com.zmart.api.product.util.ProductConstants.PRODUCTS;

@Builder
@JsonPropertyOrder({"count", "productDtoList"})
public record ProductCreationResponse(
        @Schema(example = "1")
        @NotNull
        @PositiveOrZero
        Integer count,
        @JsonProperty(PRODUCTS)
        @ValidUniqueCollection
        List<@NotNull ProductDto> productDtoList
) implements ProductResponse {
}
