package com.zmart.api.product.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zmart.api.product.validation.ValidAlpha;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.Optional;

import static com.zmart.api.product.util.ProductConstants.DATA_VIEW;
import static com.zmart.api.product.util.ProductUtility.trimQueryParam;

public record ProductByItemCodeRequest(
        @Schema(defaultValue = "MoonBerr")
        @ValidAlpha
        String itemCode,
        @Valid
        @Nullable
        @JsonProperty(DATA_VIEW)
        DataViewDto dataViewDto
) implements ProductRequest, SortableProductRequest {

        @ConstructorBinding
        public ProductByItemCodeRequest(String itemCode, DataViewDto dataViewDto) {
                this.dataViewDto = Optional.ofNullable(dataViewDto)
                        .orElse(DataViewDto.getDataViewDtoDefault());
                this.itemCode = trimQueryParam(itemCode);
        }

}
