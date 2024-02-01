package com.zmart.api.product.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.Optional;

import static com.zmart.api.product.util.ProductConstants.DATA_VIEW;

public record ProductAllProdsRequest(
        @Valid
        @Nullable
        @JsonProperty(DATA_VIEW)
        DataViewDto dataViewDto
) implements ProductRequest, SortableProductRequest {

        @ConstructorBinding
        public ProductAllProdsRequest(DataViewDto dataViewDto) {
                this.dataViewDto = Optional.ofNullable(dataViewDto)
                        .orElse(DataViewDto.getDataViewDtoDefault());
        }
}
