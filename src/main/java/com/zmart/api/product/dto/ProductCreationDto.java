package com.zmart.api.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zmart.api.product.dto.request.ProductCreationRequest;
import com.zmart.api.product.dto.request.ProductRequest;
import com.zmart.api.product.validation.ValidAlpha;
import com.zmart.api.product.validation.ValidAlphaWithSpace;
import com.zmart.api.product.validation.ValidCreateProductQualities;
import com.zmart.api.product.validation.ValidQualityOperation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.With;

import java.util.List;

import static com.zmart.api.product.util.ProductConstants.STOCK;
import static com.zmart.api.product.util.ProductUtility.trimQueryParam;

/**
 * {@link #quality} validated by:
 * <br>{@link ValidCreateProductQualities}
 * <br>which annotates:
 * <br>{@link ProductCreationRequest}
 * */
@With
@Builder
public record ProductCreationDto(
        @Schema(defaultValue = "Moonberries", minLength = 1, maxLength = 50)
        @ValidAlphaWithSpace
        String itemName,
        @Schema(defaultValue = "MoonBerr", minLength = 1, maxLength = 50)
        @ValidAlpha
        String itemCode,
        //Swagger schema example at InventoryDto.quality
        //Validated on request class/method-level
        @Schema(defaultValue = "20")
        Integer quality,
        @Schema(defaultValue = "0", maxLength = 3)
        @ValidQualityOperation
        Integer qualityOperation,
        @JsonProperty(STOCK)
        @NotNull
        List<@Valid @NotNull InventoryDto> inventoryDtoList
) implements ProductRequest {

        public ProductCreationDto(String itemName, String itemCode, Integer quality,
                                  Integer qualityOperation, List<InventoryDto> inventoryDtoList) {
                this.itemName = trimQueryParam(itemName);
                this.itemCode = trimQueryParam(itemCode);
                this.quality = quality;
                this.qualityOperation = qualityOperation;
                this.inventoryDtoList = inventoryDtoList;
        }
}
