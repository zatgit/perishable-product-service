package com.zmart.api.product.dto.request;

import com.zmart.api.product.validation.ValidPositiveNumeral;
import com.zmart.api.product.validation.ValidQueryLimit;
import com.zmart.api.product.validation.ValidSortOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.springframework.data.domain.Sort;

import java.util.Objects;

import static com.zmart.api.product.util.ProductConstants.ITEM_NAME;
import static com.zmart.api.product.util.ProductConstants.QUALITY;
import static com.zmart.api.product.util.ProductConstants.SELL_BY;
import static com.zmart.api.product.util.ProductConstants.STOCK_DATE;

@Builder
public record ProductQueryParamsDto(
    @ValidPositiveNumeral
    @Schema(description = "Number of days in the future to calculate sellBy and quality for",
            minimum = "0", maximum = "1000",
            example = "5", defaultValue = "0")
    Integer dayOffset,
    @NotNull
    @ValidQueryLimit
    @Schema(description = "Max number of products to return",
            minimum = "1", maximum = "500",
            example = "100", defaultValue = "100")
    Integer limit,
    @NotBlank
    @Pattern(
            regexp = SELL_BY + '|' + ITEM_NAME + '|' + QUALITY + '|' + STOCK_DATE,
            message = "must match " + SELL_BY + " or " + ITEM_NAME
                    + " or " + QUALITY + " or " + STOCK_DATE)
    @Schema(allowableValues = {SELL_BY, ITEM_NAME, QUALITY, STOCK_DATE}, defaultValue = ITEM_NAME)
    String sortBy,
    @ValidSortOrder
    @Schema(allowableValues = {"ASC", "DESC"}, defaultValue = "ASC")
    String sortOrder
) implements SortableQuery, PageableQuery {
    public ProductQueryParamsDto {
        dayOffset = Objects.requireNonNullElse(dayOffset, ProductQueryParamsDtoDefaultState.DAY_OFFSET.intValue);
        limit = Objects.requireNonNullElse(limit, ProductQueryParamsDtoDefaultState.LIMIT.intValue);
        sortBy = Objects.requireNonNullElse(sortBy, ProductQueryParamsDtoDefaultState.SORT_BY.strValue);
        sortOrder = Objects.requireNonNullElse(sortOrder, ProductQueryParamsDtoDefaultState.SORT_ORDER.strValue);
    }

    public static ProductQueryParamsDto getProductQueryParamsDtoDefaults() {
        return ProductQueryParamsDto.builder()
                .dayOffset(ProductQueryParamsDtoDefaultState.DAY_OFFSET.intValue)
                .limit(ProductQueryParamsDtoDefaultState.LIMIT.intValue)
                .sortBy(ProductQueryParamsDtoDefaultState.SORT_BY.strValue)
                .sortOrder(ProductQueryParamsDtoDefaultState.SORT_ORDER.strValue)
                .build();
    }

    protected enum ProductQueryParamsDtoDefaultState {
        DAY_OFFSET(0),
        LIMIT(500),
        SORT_BY(ITEM_NAME),
        SORT_ORDER(Sort.Direction.ASC.name());

        protected Integer intValue;
        protected String strValue;

        ProductQueryParamsDtoDefaultState(int intValue) {
            this.intValue = intValue;
        }

        ProductQueryParamsDtoDefaultState(String strValue) {
            this.strValue = strValue;
        }
    }
}
