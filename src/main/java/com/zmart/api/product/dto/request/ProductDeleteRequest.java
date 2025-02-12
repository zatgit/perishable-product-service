package com.zmart.api.product.dto.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zmart.api.product.validation.ValidUniqueCollection;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record ProductDeleteRequest(
        @ValidUniqueCollection
        @JsonAlias({"uuid", "id", "ids"})
        @JsonProperty("uuids")
        List<@NotNull UUID> uuidList
) implements ProductRequest {
}
