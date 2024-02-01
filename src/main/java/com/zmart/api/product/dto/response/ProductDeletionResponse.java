package com.zmart.api.product.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.zmart.api.product.validation.ValidUniqueCollection;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

import static com.zmart.api.product.util.ProductConstants.DATA;

/*@JsonTypeName & @JsonTypeInfo required for Schema#implementation()
because Swagger won't use them from ProductResponse.*/
@Builder
@JsonTypeName(DATA)
@JsonPropertyOrder({"count", "deletedProducts"})
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
public record ProductDeletionResponse(
        @Schema(example = "1")
        @NotNull
        @PositiveOrZero
        Integer count,
        @JsonProperty("deleted")
        @ValidUniqueCollection
        List<@NotNull UUID> deletedProducts
) implements ProductResponse {
}
