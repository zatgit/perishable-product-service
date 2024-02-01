package com.zmart.api.product.service;

import com.zmart.api.product.cache.CacheEvictCreate;
import com.zmart.api.product.cache.CacheEvictDelete;
import com.zmart.api.product.cache.CacheableAllProducts;
import com.zmart.api.product.cache.CacheableId;
import com.zmart.api.product.cache.CacheableItemCode;
import com.zmart.api.product.cache.CacheableItemName;
import com.zmart.api.product.cache.CacheableQuality;
import com.zmart.api.product.dto.InventoryDto;
import com.zmart.api.product.dto.ProductDto;
import com.zmart.api.product.dto.request.ProductAllProdsRequest;
import com.zmart.api.product.dto.request.ProductByIdRequest;
import com.zmart.api.product.dto.request.ProductByItemCodeRequest;
import com.zmart.api.product.dto.request.ProductByItemNameRequest;
import com.zmart.api.product.dto.request.ProductCreationRequest;
import com.zmart.api.product.dto.request.ProductDeletionRequest;
import com.zmart.api.product.dto.request.ProductsByQualityRequest;
import com.zmart.api.product.dto.response.ProductAllProdsResponse;
import com.zmart.api.product.dto.response.ProductByIdResponse;
import com.zmart.api.product.dto.response.ProductByItemCodeResponse;
import com.zmart.api.product.dto.response.ProductByItemNameResponse;
import com.zmart.api.product.dto.response.ProductCreationResponse;
import com.zmart.api.product.dto.response.ProductDeletionResponse;
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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

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
    public ProductAllProdsResponse getAllProducts(
            @NotNull final ProductAllProdsRequest request) {
        final Sort sort = helper.getProductSort(request);
        final List<Product> result = productRepository.findAll(null,
                QUERY_OFFSET, request.dataViewDto().limit(), sort);
        return ProductAllProdsResponse.builder()
                .productDtoList(getSortableDailyInventoryTracking(
                        PRODUCT_MAPPER.productEntityListToProductDtoList(result),
                        request.dataViewDto().dayOffset(), sort))
                .count(getInventoryCount(result))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @CacheableId
    public ProductByIdResponse getProductById(
            @NotNull final ProductByIdRequest request) {
        final Product result = productRepository.findBy(
                helper.getProductSpecification(UUID, request.uuid()));
        return ProductByIdResponse.builder()
                .productDto(result != null ? getDailyInventoryTracking(
                        PRODUCT_MAPPER.productEntityToProductDto(result), request.dayOffset())
                        : ProductDto.builder().build())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @CacheableItemName
    public ProductByItemNameResponse getProductByItemName(
            @NotNull final ProductByItemNameRequest request) {
        final Product result = productRepository.findBy(
                helper.getProductSpecification(ITEM_NAME, request.itemName()),
                QUERY_OFFSET, request.dataViewDto().limit());
        return ProductByItemNameResponse.builder()
                .productDto(result != null ? getSortableDailyInventoryTracking(
                        PRODUCT_MAPPER.productEntityToProductDto(result),
                        request.dataViewDto().dayOffset(), helper.getProductSort(request))
                        : ProductDto.builder().build())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @CacheableItemCode
    public ProductByItemCodeResponse getProductByItemCode(
            @NotNull final ProductByItemCodeRequest request) {
        final Product result = productRepository.findBy(
                helper.getProductSpecification(ITEM_CODE, request.itemCode()),
                QUERY_OFFSET, request.dataViewDto().limit());
        return ProductByItemCodeResponse.builder()
                .productDto(result != null ? getSortableDailyInventoryTracking(
                        PRODUCT_MAPPER.productEntityToProductDto(result),
                        request.dataViewDto().dayOffset(), helper.getProductSort(request))
                        : ProductDto.builder().build())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @CacheableQuality
    public ProductsByQualityResponse getProductsByQuality(
            @NotNull final ProductsByQualityRequest request) {
        final Sort sort = helper.getProductSort(request);
        final List<Product> result = productRepository.findAll(
                helper.getProductSpecification(QUALITY, request.quality()),
                QUERY_OFFSET, request.dataViewDto().limit(), sort);
        return ProductsByQualityResponse.builder()
                .productDtoList(getSortableDailyInventoryTracking(
                        PRODUCT_MAPPER.productEntityListToProductDtoList(result),
                        request.dataViewDto().dayOffset(), sort))
                .count(getInventoryCount(result))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @CacheEvictCreate
    public ProductCreationResponse createProducts(
            @NotNull final ProductCreationRequest request) {
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
        return ProductCreationResponse.builder()
                .productDtoList(PRODUCT_MAPPER.productEntityListToProductDtoList(result))
                .count(getInventoryCount(result))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @CacheEvictDelete
    public ProductDeletionResponse deleteProductsById(
            @NotNull final ProductDeletionRequest productDeletionRequest) {
        final List<Inventory> result = inventoryRepository.deleteAllByUuidIn(
                productDeletionRequest.uuidList());
        return ProductDeletionResponse.builder()
                .deletedProducts(result.stream().map(Inventory::getUuid).toList())
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
