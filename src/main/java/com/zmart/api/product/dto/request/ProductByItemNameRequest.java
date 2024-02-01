package com.zmart.api.product.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zmart.api.product.validation.ValidAlphaWithSpace;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.Optional;

import static com.zmart.api.product.util.ProductConstants.DATA_VIEW;
import static com.zmart.api.product.util.ProductUtility.trimQueryParam;

public record ProductByItemNameRequest(
        @Schema(defaultValue = "Moonberries")
        @ValidAlphaWithSpace
        String itemName,
        @Valid
        @Nullable
        @JsonProperty(DATA_VIEW)
        DataViewDto dataViewDto
) implements ProductRequest, SortableProductRequest {

        @ConstructorBinding
        public ProductByItemNameRequest(String itemName, DataViewDto dataViewDto) {
                this.dataViewDto = Optional.ofNullable(dataViewDto)
                        .orElse(DataViewDto.getDataViewDtoDefault());
                this.itemName = trimQueryParam(itemName);
        }
}
