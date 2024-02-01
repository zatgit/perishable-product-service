package com.zmart.api.product.dto;

import com.zmart.api.product.entity.Inventory;
import com.zmart.api.product.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static com.zmart.api.product.util.ProductConstants.INVENTORY_LIST;

@Mapper
public interface ProductMapper {

    ProductMapper PRODUCT_MAPPER = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = INVENTORY_LIST, target = "inventoryDtoList")
    ProductDto productEntityToProductDto(
            Product product);

    List<ProductDto> productEntityListToProductDtoList(
            List<Product> productList);

    List<Product> productCreationDtoListToProductList(
            List<ProductCreationDto> productCreationDtoList);

    @Mapping(source = "inventoryDtoList", target = "inventoryList")
    Product productCreationDtoToProduct(
            ProductCreationDto productCreationDto);

    List<InventoryDto> inventoryEntityListToInventoryDtoList(
            List<Inventory> inventoryList);
}