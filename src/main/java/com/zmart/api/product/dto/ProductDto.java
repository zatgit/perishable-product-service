package com.zmart.api.product.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.zmart.api.product.validation.ValidAlpha;
import com.zmart.api.product.validation.ValidAlphaWithSpace;
import com.zmart.api.product.validation.ValidQualityOperation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.With;

import java.io.Serializable;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.zmart.api.product.util.ProductConstants.STOCK;

@JsonPropertyOrder({"itemName", "itemCode", "qualityOperation", STOCK})
@JsonInclude(NON_NULL)
@With
@Builder
public record ProductDto(
        @Schema(example = "Moonberries", minLength = 1, maxLength = 50)
        @ValidAlphaWithSpace
        String itemName,
        @Schema(example = "MoonBerr", minLength = 1, maxLength = 50)
        @ValidAlpha
        String itemCode,
        @Schema(allowableValues = {"0", "1", "2", "3"}, defaultValue = "0")
        @ValidQualityOperation
        Integer qualityOperation,
        @JsonProperty(STOCK)
        @NotNull
        List<@NotNull InventoryDto> inventoryDtoList
) implements Serializable /*Serial for caching*/ {
}
