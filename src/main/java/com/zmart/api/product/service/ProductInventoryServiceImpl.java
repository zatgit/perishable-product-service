package com.zmart.api.product.service;

import com.zmart.api.cache.aspect.CacheStatusTracked;
import com.zmart.api.cache.product.CacheableAllProducts;
import com.zmart.api.cache.product.CacheableId;
import com.zmart.api.cache.product.CacheableItemCode;
import com.zmart.api.cache.product.CacheableItemName;
import com.zmart.api.cache.product.CacheableQuality;
import com.zmart.api.product.dto.InventoryDto;
import com.zmart.api.product.dto.ProductDto;
import com.zmart.api.product.dto.request.ProductCreateRequest;
import com.zmart.api.product.dto.request.ProductDeleteRequest;
import com.zmart.api.product.dto.request.ProductQueryParamsDto;
import com.zmart.api.product.dto.response.ProductAllProdsResponse;
import com.zmart.api.product.dto.response.ProductByIdResponse;
import com.zmart.api.product.dto.response.ProductByItemCodeResponse;
import com.zmart.api.product.dto.response.ProductByItemNameResponse;
import com.zmart.api.product.dto.response.ProductCreateResponse;
import com.zmart.api.product.dto.response.ProductDeleteResponse;
import com.zmart.api.product.dto.response.ProductsByQualityResponse;
import com.zmart.api.product.entity.Inventory;
import com.zmart.api.product.entity.Product;
import com.zmart.api.product.repository.InventoryRepository;
import com.zmart.api.product.repository.ProductRepository;
import com.zmart.api.product.util.QualityOperationEnum;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

import static com.zmart.api.exception.ExceptionUtils.getProductNotFoundMessage;
import static com.zmart.api.product.dto.ProductMapper.PRODUCT_MAPPER;
import static com.zmart.api.product.util.ProductConstants.ITEM_CODE;
import static com.zmart.api.product.util.ProductConstants.ITEM_NAME;
import static com.zmart.api.product.util.ProductConstants.PRODUCTS;
import static com.zmart.api.product.util.ProductConstants.QUALITY;
import static com.zmart.api.product.util.ProductConstants.UUID;
import static com.zmart.api.product.util.ProductUtility.getInventoryCount;

@Service
@Validated
@CacheConfig(cacheNames = PRODUCTS)
@SuppressWarnings({"java:S3358", "java:S1659"})
public class ProductInventoryServiceImpl implements ProductInventoryService {

    private static final int QUERY_OFFSET = 0;
    @Value("${pps.product.properties.quality.maximum:50}")
    private Integer maxQuality;
    @Value("${pps.timezone.zoneId:UTC+00:00}")
    private String zoneId;
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final ProductInventoryServiceImplHelper helper;

    @Autowired
    public ProductInventoryServiceImpl(
            final ProductRepository productRepository,
            final InventoryRepository inventoryRepository,
            final ProductInventoryServiceImplHelper helper) {
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
        this.helper = helper;
    }

    /**
     * {@inheritDoc}
    */
    @Transactional
    @CacheableAllProducts
    @CacheStatusTracked
    public ProductAllProdsResponse getAllProducts(
            @NotNull final ProductQueryParamsDto request) {
        final Sort sort = helper.getProductSort(request);
        final List<Product> result = productRepository.findAll(null,
                QUERY_OFFSET, request.limit(), sort);
        return ProductAllProdsResponse.builder()
                .productDtoList(getSortableDailyInventoryTracking(
                        PRODUCT_MAPPER.productEntityListToProductDtoList(result),
                        request.dayOffset(), sort))
                .count(getInventoryCount(result))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @CacheableId
    @CacheStatusTracked
    public ProductByIdResponse getProductById(
            @NotNull UUID uuid,
            @NotNull Integer dayOffset) {
        final Product result = Optional.ofNullable(productRepository.findBy(
                helper.getProductSpecification(UUID, uuid)))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        getProductNotFoundMessage(UUID, String.valueOf(uuid))));
        return ProductByIdResponse.builder()
                .productDto(getDailyInventoryTracking(
                        PRODUCT_MAPPER.productEntityToProductDto(result), dayOffset))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @CacheableItemName
    @CacheStatusTracked
    public ProductByItemNameResponse getProductByItemName(
            @NotNull final String itemName,
            @NotNull final ProductQueryParamsDto request) {
        final Product result = Optional.ofNullable(productRepository.findBy(
                helper.getProductSpecification(ITEM_NAME, itemName),
                        QUERY_OFFSET, request.limit()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        getProductNotFoundMessage(ITEM_NAME, itemName)));
        return ProductByItemNameResponse.builder()
                .productDto(getSortableDailyInventoryTracking(
                        PRODUCT_MAPPER.productEntityToProductDto(result),
                        request.dayOffset(), helper.getProductSort(request)))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @CacheableItemCode
    @CacheStatusTracked
    public ProductByItemCodeResponse getProductByItemCode(
            @NotNull final String itemCode,
            @NotNull final ProductQueryParamsDto request) {
        final Product result = Optional.ofNullable(productRepository.findBy(
                helper.getProductSpecification(ITEM_CODE, itemCode),
                        QUERY_OFFSET, request.limit()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        getProductNotFoundMessage(ITEM_CODE, itemCode)));
        return ProductByItemCodeResponse.builder()
                .productDto(getSortableDailyInventoryTracking(
                        PRODUCT_MAPPER.productEntityToProductDto(result),
                        request.dayOffset(), helper.getProductSort(request)))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @CacheableQuality
    @CacheStatusTracked
    public ProductsByQualityResponse getProductsByQuality(
            @NotNull final Integer quality,
            @NotNull final ProductQueryParamsDto request) {
        final Sort sort = helper.getProductSort(request);
        final List<Product> result = productRepository.findAll(
                helper.getProductSpecification(QUALITY, quality),
                QUERY_OFFSET, request.limit(), sort);
        return ProductsByQualityResponse.builder()
                .productDtoList(getSortableDailyInventoryTracking(
                        PRODUCT_MAPPER.productEntityListToProductDtoList(result),
                        request.dayOffset(), sort))
                .count(getInventoryCount(result))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @CacheEvict(value = PRODUCTS, allEntries = true)
    public ProductCreateResponse createProducts(
            @NotNull final ProductCreateRequest request) {
        final List<Product> productList = PRODUCT_MAPPER.productCreationDtoListToProductList(
                request.productCreationDtoList()).stream()
                    .map(p ->
                            p.withInventoryList(
                                    p.getInventoryList().stream()
                                            .map(i -> i
                                                    .withItemName(p.getItemName())
                                                    .withItemCode(p.getItemCode())
                                                    //inventory quality overrides product quality if specified
                                                    .withQuality(i.getQuality() != null ? i.getQuality() : p.getQuality()))
                                            .toList()))
                    .toList();
        final List<Product> result = productRepository.saveAll(productList);
        return ProductCreateResponse.builder()
                .productDtoList(PRODUCT_MAPPER.productEntityListToProductDtoList(result))
                .count(getInventoryCount(result))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @CacheEvict(value = PRODUCTS, allEntries = true)
    public ProductDeleteResponse deleteProductsById(
            @NotNull final ProductDeleteRequest productDeleteRequest) {
        final List<Inventory> deletedProducts = inventoryRepository.deleteAllByUuidIn(
                productDeleteRequest.uuidList());
        List<UUID> deletedUuids = deletedProducts.stream()
                .map(Inventory::getUuid)
                .toList();
        List<UUID> notFoundUuids = new ArrayList<>(productDeleteRequest.uuidList());
        notFoundUuids.removeAll(deletedUuids);
        return ProductDeleteResponse.builder()
                .deletedProductUuids(deletedUuids)
                .notFoundUuids(notFoundUuids)
                .build();
    }


    /**
     * Processes product DTO inventories by quality operation.
     * <br>See {@link QualityOperationEnum} for description of each quality operation.
     *
     * @param productDto
     * @param futureDaysOffset
     * @return product DTO with inventories processed by quality operation
     */
    @NotNull
    private ProductDto processQualityOperations(@NotNull final ProductDto productDto,
                                                @NotNull final Integer futureDaysOffset) {
        final ZonedDateTime currentDate = ZonedDateTime.now(ZoneId.of(zoneId));
        switch (QualityOperationEnum.values()[productDto.qualityOperation()]) {
            case QUALITY_OPERATION_0:
                final BiFunction<InventoryDto, Integer, Integer> degradedQuality =
                        (inventoryDto, dayOffset) ->
                                Math.max(0, IntStream.range(0, dayOffset)
                                        .map(i -> inventoryDto.sellBy() > 0 ? -1 : -2)
                                        .reduce(inventoryDto.quality(), Integer::sum));
                return productDto.withInventoryDtoList(
                        productDto.inventoryDtoList().stream()
                                .map(i -> i.withQuality(degradedQuality.apply(i, futureDaysOffset))
                                        .withSellBy(helper.decrementSellByWithOffsets(currentDate, futureDaysOffset, i))
                                        .withSellByDate(helper.getSellByDate(i))
                                        .withFutureDate(helper.getFutureDateWithOffset(currentDate, futureDaysOffset))
                                        .withCurrentDate(currentDate))
                                .toList());
            case QUALITY_OPERATION_1:
                return productDto.withInventoryDtoList(
                        productDto.inventoryDtoList().stream()
                                .map(i -> i.withQuality(Math.min(i.quality() + futureDaysOffset, maxQuality))
                                        .withSellBy(helper.decrementSellByWithOffsets(currentDate, futureDaysOffset, i))
                                        .withSellByDate(helper.getSellByDate(i))
                                        .withFutureDate(helper.getFutureDateWithOffset(currentDate, futureDaysOffset))
                                        .withCurrentDate(currentDate))
                                .toList());
            case QUALITY_OPERATION_2:
                final Integer sellByExpiration = 0, doubleQualityIncrementTrigger = 10;
                return productDto.withInventoryDtoList(
                        productDto.inventoryDtoList().stream()
                                .map(i -> i.withSellBy(helper.decrementSellByWithOffsets(currentDate, futureDaysOffset, i))
                                        .withQuality(i.sellBy() < sellByExpiration
                                                ? 0
                                                : i.sellBy() <= doubleQualityIncrementTrigger
                                                    ? i.quality() + 2
                                                    : Math.min(i.quality() + futureDaysOffset, maxQuality))
                                        .withSellByDate(helper.getSellByDate(i))
                                        .withFutureDate(helper.getFutureDateWithOffset(currentDate, futureDaysOffset))
                                        .withCurrentDate(currentDate))
                                .toList());
            case QUALITY_OPERATION_3:
                return productDto.withInventoryDtoList(
                        productDto.inventoryDtoList().stream()
                                .map(i -> i.withSellBy(helper.decrementSellByWithOffsets(currentDate, futureDaysOffset, i))
                                        .withSellByDate(helper.getSellByDate(i))
                                        .withFutureDate(helper.getFutureDateWithOffset(currentDate, futureDaysOffset))
                                        .withCurrentDate(currentDate))
                                .toList());
        }
        return productDto;
    }

    /**
     * Gets product DTO with inventories processed by quality operation.
     *
     * @param productDto the product DTO
     * @param futureDaysOffset the number of future days from current date
     * @return product DTO with inventories processed by quality operation
     */
    @NotNull
    private ProductDto getDailyInventoryTracking(@NotNull final ProductDto productDto,
                                         @NotNull final Integer futureDaysOffset) {
        return processQualityOperations(productDto, futureDaysOffset);
    }

    /**
     * Gets sortable product DTO with inventories processed by quality operation.
     *
     * @param productDto the product DTO
     * @param futureDaysOffset the number of future days from current date
     * @param sort sorting properties
     * @return sortable product DTO with inventories processed by quality operation
     */
    @NotNull
    private ProductDto getSortableDailyInventoryTracking(@NotNull final ProductDto productDto,
                                                 @NotNull final Integer futureDaysOffset,
                                                 @NotNull final Sort sort) {
        return helper.sortChildInventories(sort, processQualityOperations(productDto, futureDaysOffset));
    }

    /**
     * Gets sortable product DTOs with inventories processed by quality operation.
     *
     * @param productDtoList the list of product DTOs
     * @param futureDaysOffset the number of future days from current date
     * @param sort sorting properties
     * @return list of sortable product DTOs with inventories processed by quality operation
     */
    @NotNull
    private List<ProductDto> getSortableDailyInventoryTracking(@NotNull final List<ProductDto> productDtoList,
                                                       @NotNull final Integer futureDaysOffset,
                                                       @NotNull final Sort sort) {
        return helper.sortChildInventories(sort, productDtoList.stream()
                .parallel()
                .map(p -> processQualityOperations(p, futureDaysOffset))
                .toList());
    }
}
