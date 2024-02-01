package com.zmart.api.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.zmart.api.product.dto.request.ProductCreationRequest;
import com.zmart.api.product.util.ProductConstants;
import com.zmart.api.product.validation.ValidCreateProductQualities;
import com.zmart.api.product.validation.ValidPositiveNumeral;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.With;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * {@link #quality} validated by:
 * <br>{@link ValidCreateProductQualities}
 * <br>which annotates:
 * <br>{@link ProductCreationRequest}
 * */
@JsonPropertyOrder({"uuid", "futureDate", "sellByDate", "stockDate", "currentDate", "sellBy", "quality"})
@JsonInclude(NON_NULL)
@With
@Builder
public record InventoryDto(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY,
                defaultValue = "b252b455-f9b3-4fa5-be18-0b25e8d8b074")
        UUID uuid,
        @Schema(type = swaggerSchemaTypeString, format = swaggerSchemaFormatDate,
                defaultValue = "2077-01-23", accessMode = Schema.AccessMode.READ_ONLY)
        @JsonFormat(pattern = dateFormatRfc3339fullDate)
        ZonedDateTime stockDate,
        @Schema(type = swaggerSchemaTypeString, format = swaggerSchemaFormatDate,
                defaultValue = "2077-01-28", accessMode = Schema.AccessMode.READ_ONLY)
        @JsonFormat(pattern = dateFormatRfc3339fullDate)
        ZonedDateTime futureDate,
        @Schema(type = swaggerSchemaTypeString, format = swaggerSchemaFormatDate,
                defaultValue = "2077-02-06", accessMode = Schema.AccessMode.READ_ONLY)
        @JsonFormat(pattern = dateFormatRfc3339fullDate)
        ZonedDateTime sellByDate,
        @Schema(type = swaggerSchemaTypeString, format = swaggerSchemaFormatDate,
                defaultValue = "2077-01-23", accessMode = Schema.AccessMode.READ_ONLY)
        @JsonFormat(pattern = dateFormatRfc3339fullDate)
        ZonedDateTime currentDate,
        @JsonProperty(ProductConstants.SELL_BY)
        @ValidPositiveNumeral
        @Schema(defaultValue = "15", maxLength = 1000)
        Integer sellBy,
        @Schema(defaultValue = "20", maxLength = 50)
        //Validated on request class/method-level
        Integer quality
) implements Serializable /*Serial for caching*/ {
        private static final String dateFormatRfc3339fullDate = "yyyy-MM-dd";
        private static final String swaggerSchemaTypeString = "string";
        private static final String swaggerSchemaFormatDate = "date";
}
