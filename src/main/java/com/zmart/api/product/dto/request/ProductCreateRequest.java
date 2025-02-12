package com.zmart.api.product.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zmart.api.product.dto.ProductCreationDto;
import com.zmart.api.product.validation.ValidCreateProductQualities;
import com.zmart.api.product.validation.ValidUniqueCollection;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.With;

import java.util.List;

import static com.zmart.api.product.util.ProductConstants.PRODUCTS;

@With
@Builder
@ValidCreateProductQualities
public record ProductCreateRequest(
        @JsonProperty(PRODUCTS)
        @ValidUniqueCollection
        @NotNull
        List<@Valid @NotNull ProductCreationDto> productCreationDtoList
) implements ProductRequest {
}



