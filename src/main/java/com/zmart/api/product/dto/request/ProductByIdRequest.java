package com.zmart.api.product.dto.request;

import com.zmart.api.product.validation.ValidPositiveNumeral;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;
import java.util.UUID;

public record ProductByIdRequest(
        @NotNull
        UUID uuid,
        @Schema(defaultValue = "0")
        @ValidPositiveNumeral
        Integer dayOffset
) implements ProductRequest {
        public ProductByIdRequest {
                dayOffset = Objects.requireNonNullElse(dayOffset, DataViewDto.DataViewDtoDefaultEnum.DAY_OFFSET.intValue);
        }
}
