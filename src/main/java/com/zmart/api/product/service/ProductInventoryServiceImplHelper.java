package com.zmart.api.product.service;

import com.zmart.api.product.dto.InventoryDto;
import com.zmart.api.product.dto.ProductDto;
import com.zmart.api.product.dto.request.SortableProductRequest;
import com.zmart.api.product.entity.Inventory;
import com.zmart.api.product.entity.Product;
import jakarta.annotation.Nullable;
import jakarta.persistence.criteria.Join;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.BiFunction;

import static com.zmart.api.product.util.ProductConstants.INVENTORY_LIST;
import static com.zmart.api.product.util.ProductConstants.ITEM_NAME;
import static com.zmart.api.product.util.ProductConstants.QUALITY;
import static com.zmart.api.product.util.ProductConstants.SELL_BY;
import static com.zmart.api.product.util.ProductConstants.STOCK_DATE;

@Service
@Validated
@SuppressWarnings("java:S1125")
public class ProductInventoryServiceImplHelper {

    @Nullable
    ZonedDateTime getFutureDateWithOffset(@NotNull final ZonedDateTime currentDate,
                                          @Nullable final Integer futureDaysOffset) {
        return futureDaysOffset != null && futureDaysOffset > 0
                ? currentDate.plusDays(futureDaysOffset)
                : null;
    }

    @NotNull
    Specification<Product> getProductSpecification(@NotNull final String columnName,
                                                   @NotNull final Object searchValue) {
        return (root, query, builder) -> {
            final Join<Product, Inventory> inventoryJoin = root.join(INVENTORY_LIST);
            return builder.equal(inventoryJoin.get(columnName), searchValue);
        };
    }

    @NotNull
    Sort getProductSort(@NotNull final SortableProductRequest request) {
        return Sort.by(
                Sort.Direction.valueOf(
                        request.dataViewDto().sortOrder().toUpperCase(Locale.ROOT)),
                String.format("%s.%s", INVENTORY_LIST, request.dataViewDto().sortBy()));
    }

    BiFunction<List<InventoryDto>, Sort, List<InventoryDto>> sortedInventories =
            (inventoryDtoList, sort) -> {
                switch (getSortProperty(sort)) {
                    case SELL_BY:
                        return inventoryDtoList.stream()
                                .sorted(isInventorySortAscending(sort)
                                        ? Comparator.comparing(InventoryDto::sellBy)
                                        : Comparator.comparing(InventoryDto::sellBy).reversed())
                                .toList();
                    case QUALITY:
                        return inventoryDtoList.stream()
                                .sorted(isInventorySortAscending(sort)
                                        ? Comparator.comparing(InventoryDto::quality)
                                        : Comparator.comparing(InventoryDto::quality).reversed())
                                .toList();
                    case STOCK_DATE:
                        return inventoryDtoList.stream()
                                .sorted(isInventorySortAscending(sort)
                                        ? Comparator.comparing(InventoryDto::stockDate)
                                        : Comparator.comparing(InventoryDto::stockDate).reversed())
                                .toList();
                    default:
                        return inventoryDtoList;
                }
            };

    @NotNull
    ProductDto sortChildInventories(@NotNull final Sort sort,
                                    @NotNull final ProductDto productDto) {
        return productDto.withInventoryDtoList(
                sortedInventories.apply(productDto.inventoryDtoList(), sort));
    }

    @NotNull
    List<ProductDto> sortChildInventories(@NotNull final Sort sort,
                                          @NotNull final List<ProductDto> productDtoList) {
        return productDtoList.stream()
                .map(p -> Optional.ofNullable(p.inventoryDtoList())
                        .map(i -> p.withInventoryDtoList(sortedInventories.apply(i, sort)))
                        .orElse(p))
                .toList();
    }

    @NotNull
    private boolean isInventorySortAscending(@NotNull final Sort sort) {
        final Sort.Order order = sort.getOrderFor(sort.get().findFirst().stream()
                .map(Sort.Order::getProperty)
                .toArray()[0].toString());
        return order != null ? order.getDirection().isAscending() : true;
    }

    @NotNull
    private String getSortProperty(@NotNull final Sort sort) {
        return Optional.ofNullable(sort.get().findFirst().stream()
                .map(Sort.Order::getProperty)
                .toArray()[0].toString()
                .split("\\.")[1]).orElse(ITEM_NAME);
    }

    @NotNull ZonedDateTime getSellByDate(@NotNull final InventoryDto inventoryDto) {
        return inventoryDto.stockDate().plusDays(inventoryDto.sellBy());
    }

    @NotNull Integer decrementSellByWithOffsets(@NotNull final ZonedDateTime currentDate,
                                                @NotNull final Integer futureDaysOffset,
                                                @NotNull final InventoryDto i) {
        return i.sellBy() - futureDaysOffset -
                getDaysBetweenStockDateAndCurrentDate(currentDate, i.stockDate());
    }

    @NotNull
    private Integer getDaysBetweenStockDateAndCurrentDate(@NotNull final ZonedDateTime currentDate,
                                                          @NotNull final ZonedDateTime stockDate) {
        return Math.toIntExact(ChronoUnit.DAYS.between(currentDate, stockDate));
    }
}
