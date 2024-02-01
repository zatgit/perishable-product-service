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
public record DataViewDto(
    @Schema(defaultValue = "0")
    @ValidPositiveNumeral
    Integer dayOffset,
    @Schema(defaultValue = "100")
    @NotNull
    @ValidQueryLimit
    Integer limit,
    @Schema(allowableValues = {SELL_BY, ITEM_NAME, QUALITY, STOCK_DATE}, defaultValue = ITEM_NAME)
    @NotBlank
    @Pattern(
            regexp = SELL_BY + '|' + ITEM_NAME + '|' + QUALITY + '|' + STOCK_DATE,
            message = "must match " + SELL_BY + " or " + ITEM_NAME
                    + " or " + QUALITY + " or " + STOCK_DATE)
    String sortBy,
    @Schema(allowableValues = {"ASC", "DESC"}, defaultValue = "ASC")
    @ValidSortOrder
    String sortOrder
) {
    public DataViewDto {
        dayOffset = Objects.requireNonNullElse(dayOffset, DataViewDtoDefaultEnum.DAY_OFFSET.intValue);
        limit = Objects.requireNonNullElse(limit, DataViewDtoDefaultEnum.LIMIT.intValue);
        sortBy = Objects.requireNonNullElse(sortBy, DataViewDtoDefaultEnum.SORT_BY.strValue);
        sortOrder = Objects.requireNonNullElse(sortOrder, DataViewDtoDefaultEnum.SORT_ORDER.strValue);
    }

    public static DataViewDto getDataViewDtoDefault() {
        return DataViewDto.builder()
                .dayOffset(DataViewDtoDefaultEnum.DAY_OFFSET.intValue)
                .limit(DataViewDtoDefaultEnum.LIMIT.intValue)
                .sortBy(DataViewDtoDefaultEnum.SORT_BY.strValue)
                .sortOrder(DataViewDtoDefaultEnum.SORT_ORDER.strValue)
                .build();
    }

    protected enum DataViewDtoDefaultEnum {
        DAY_OFFSET(0),
        LIMIT(500),
        SORT_BY(ITEM_NAME),
        SORT_ORDER(Sort.Direction.ASC.name());

        protected Integer intValue;
        protected String strValue;

        DataViewDtoDefaultEnum(int intValue) {
            this.intValue = intValue;
        }

        DataViewDtoDefaultEnum(String strValue) {
            this.strValue = strValue;
        }
    }
}
