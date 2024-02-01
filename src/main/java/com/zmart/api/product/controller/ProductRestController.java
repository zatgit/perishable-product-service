package com.zmart.api.product.controller;

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
import com.zmart.api.product.service.ProductInventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.zmart.api.product.controller.swagger.SwaggerProdDescriptionConstants.*;
import static com.zmart.api.product.controller.swagger.SwaggerProdExampleJsonConstants.*;
import static com.zmart.api.product.util.ProductConstants.API_BASE_RESOURCE_PATH;
import static com.zmart.api.product.util.ProductConstants.API_VERSION;
import static com.zmart.api.product.util.ProductUtility.getSingleResourceResponseStatus;

@CrossOrigin
@RestController
@RequestMapping(
        path = API_BASE_RESOURCE_PATH + "/product",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE + "; version=" + API_VERSION)
@Tag(name = "Product", description = "Product management APIs")
public class ProductRestController {

    private final ProductInventoryService productInventoryService;

    public ProductRestController(
            final ProductInventoryService productInventoryService) {
        this.productInventoryService = productInventoryService;
    }

    @PostMapping("all")
    @Operation(summary = "Get all products", description = "Get all products in the inventory"
            + SORT_AND_OFFSET_AND_QUERY_DESC + SWAGGER_QUALITY_OP_RENDER)
    @ApiResponse(responseCode = "200", description = "Products found",
            content = {@Content(examples = {
                    @ExampleObject(name = "All Products Response 1", value = PROD_ALL_RESP_EXAMPLE_1,
                            description = """
                            Retrieves all products and off sets the current date by 5 days.
                            """ + FUTURE_DATE_5_GIVEN_QUALITY_OP_0_5_GET_ALL_DESC),
                    @ExampleObject(name = "All Products Response 2", value = PROD_ALL_RESP_EXAMPLE_2,
                            description = """
                            Retrieves all products and does *not* off set the current date.
                            """ + FUTURE_DATE_NOT_GIVEN_GET_ALL_DESC),
                    @ExampleObject(name = "All Products Response 3", value = PROD_ALL_RESP_EXAMPLE_3,
                            description = """
                            Retrieves all products and uses default ``dataView`` object.
                            """ + FUTURE_DATE_NOT_GIVEN_GET_ALL_DESC)})})
    @ApiResponse(responseCode = "404", description = "No products found",
            content = {@Content(examples = @ExampleObject(PROD_ALL_RESP_NOT_FOUND))})
    public ResponseEntity<ProductAllProdsResponse> getAllProducts(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {@Content(examples = {
                            @ExampleObject(name = "All Products Request 1", value = PROD_ALL_REQ_EXAMPLE_1,
                                    description = "``dayOffset`` is given"),
                            @ExampleObject(name = "All Products Request 2", value = PROD_ALL_REQ_EXAMPLE_2,
                                    description = "``dayOffset`` is *not* given"),
                            @ExampleObject(name = "All Products Request 3", value = "{}",
                                    description = "``dataView`` is not given")})})
            @RequestBody @Valid final ProductAllProdsRequest request) {
        final ProductAllProdsResponse response = productInventoryService.getAllProducts(request);
        return new ResponseEntity<>(response,
                response.productDtoList().stream().allMatch(p -> p.inventoryDtoList().isEmpty())
                        ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @PostMapping("id")
    @Operation(summary = "Get product by id", description = "Get product matching given ``uuid``"
            + "&nbsp;and calculate values & dates based on days to offset (``dayOffset``) & ``qualityOperation``.")
    @ApiResponse(responseCode = "200", description = "Product with matching ``uuid`` found",
            content = {@Content(examples = {
                    @ExampleObject(name = "Id Response 1", value = PROD_ID_RESP_EXAMPLE_1,
                            description = """
                            Retrieves product with the given ``uuid`` and off sets the current date by 5 days.
                            """ + FUTURE_DATE_5_GIVEN_QUALITY_OP_0_DESC),
                    @ExampleObject(name = "Id Response 2", value = PROD_ID_RESP_EXAMPLE_2,
                            description = """
                            Retrieves product with the given ``uuid`` and does *not* off set the current date.
                            """ + FUTURE_DATE_NOT_GIVEN_DESC)})})
    @ApiResponse(responseCode = "404", description = "No product found with matching ``uuid``",
            content = {@Content(examples = @ExampleObject(PROD_EMPTY_OBJ_RESP_NOT_FOUND))})
    public ResponseEntity<ProductByIdResponse> getProductById(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {@Content(examples = {
                            @ExampleObject(name = "Id Request 1", value = PROD_ID_REQ_EXAMPLE_1,
                                    description = "``dayOffset`` is given"),
                            @ExampleObject(name = "Id Request 2", value = PROD_ID_REQ_EXAMPLE_2,
                                    description = "``dayOffset`` is *not* given")})})
            @RequestBody @Valid final ProductByIdRequest request) {
        final ProductByIdResponse response = productInventoryService.getProductById(request);
        return new ResponseEntity<>(response, getSingleResourceResponseStatus(response.productDto()));
    }

    @PostMapping("item-name")
    @Operation(summary = "Get product by itemName", description = "Get product matching given ``itemName``"
            + SORT_AND_OFFSET_AND_QUERY_DESC + SWAGGER_QUALITY_OP_RENDER)
    @ApiResponse(responseCode = "200", description = "Product with matching ``itemName`` found",
            content = {@Content(examples = {
                    @ExampleObject(name = "ItemName Response 1", value = PROD_CODE_AND_NAME_RESP_EXAMPLE_1,
                            description = """
                            Retrieves product with the given ``itemName`` and off sets the current date by 5 days.
                            """ + FUTURE_DATE_5_GIVEN_QUALITY_OP_0_DESC),
                    @ExampleObject(name = "ItemName Response 2", value = PROD_CODE_AND_NAME_RESP_EXAMPLE_2,
                            description = """
                            Retrieves product with the given ``itemName`` and does *not* off set the current date.
                            """ + FUTURE_DATE_NOT_GIVEN_DESC)})})
    @ApiResponse(responseCode = "404", description = "No product found with matching ``itemName``",
            content = {@Content(examples = @ExampleObject(PROD_EMPTY_OBJ_RESP_NOT_FOUND))})
    public ResponseEntity<ProductByItemNameResponse> getProductByItemName(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {@Content(examples = {
                            @ExampleObject(name = "ItemName Request 1", value = PROD_ITEM_NAME_REQ_EXAMPLE_1,
                                    description = "``dayOffset`` is given"),
                            @ExampleObject(name = "ItemName Request 2", value = PROD_ITEM_NAME_REQ_EXAMPLE_2,
                                    description = "``dayOffset`` is *not* given")})})
            @RequestBody @Valid final ProductByItemNameRequest request) {
        final ProductByItemNameResponse response =
                productInventoryService.getProductByItemName(request);
        return new ResponseEntity<>(response, getSingleResourceResponseStatus(response.productDto()));
    }

    @PostMapping("item-code")
    @Operation(summary = "Get product by itemCode", description = "Get product matching given ``itemCode``"
            + SORT_AND_OFFSET_AND_QUERY_DESC + SWAGGER_QUALITY_OP_RENDER)
    @ApiResponse(responseCode = "200", description = "Product with matching itemCode found",
            content = {@Content(examples = {
                    @ExampleObject(name = "ItemCode Response 1", value = PROD_CODE_AND_NAME_RESP_EXAMPLE_1,
                            description = """
                            Retrieves product with the given ``itemCode`` and off sets the current date by 5 days.
                            """ + FUTURE_DATE_5_GIVEN_QUALITY_OP_0_DESC),
                    @ExampleObject(name = "ItemCode Response 2", value = PROD_CODE_AND_NAME_RESP_EXAMPLE_2,
                            description = """
                            Retrieves product with the given ``itemCode`` and does *not* off set the current date.
                            """ + FUTURE_DATE_NOT_GIVEN_DESC)})})
    @ApiResponse(responseCode = "404", description = "No product found with matching ``itemCode``",
            content = {@Content(examples = @ExampleObject(PROD_EMPTY_OBJ_RESP_NOT_FOUND))})
    public ResponseEntity<ProductByItemCodeResponse> getProductByItemCode(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {@Content(examples = {
                            @ExampleObject(name = "ItemCode Request 1", value = PROD_ITEM_CODE_REQ_EXAMPLE_1,
                                    description = "``dayOffset`` is given"),
                            @ExampleObject(name = "ItemCode Request 2", value = PROD_ITEM_CODE_REQ_EXAMPLE_2,
                                    description = "``dayOffset`` is *not* given")})})
            @RequestBody @Valid final ProductByItemCodeRequest request) {
        final ProductByItemCodeResponse response =
                productInventoryService.getProductByItemCode(request);
        return new ResponseEntity<>(response, getSingleResourceResponseStatus(response.productDto()));
    }

    @PostMapping("quality")
    @Operation(summary = "Get products by quality", description = "Get all products matching given ``quality``"
            + SORT_AND_OFFSET_AND_QUERY_DESC + SWAGGER_QUALITY_OP_RENDER)
    @ApiResponse(responseCode = "200", description = "Product(s) with matching ``quality`` found",
            content = {@Content(examples = {
                    @ExampleObject(name = "Quality Response 1", value = PROD_QUALITY_RESP_EXAMPLE_1,
                            description = """
                            Retrieves all products with the given ``quality`` and off sets the current date by 5 days.
                            """ + FUTURE_DATE_5_GIVEN_QUALITY_OP_0_DESC),
                    @ExampleObject(name = "Quality Response 2", value = PROD_QUALITY_RESP_EXAMPLE_2,
                            description = """
                            Retrieves all products with the given ``quality`` and does *not* off set the current date.
                            """ + FUTURE_DATE_NOT_GIVEN_DESC)})})
    @ApiResponse(responseCode = "404", description = "No products found with matching ``quality``",
            content = {@Content(examples = @ExampleObject(PROD_EMPTY_ARRAY_RESP_NOT_FOUND))})
    public ResponseEntity<ProductsByQualityResponse> getProductsByQuality(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {@Content(examples = {
                            @ExampleObject(name = "Quality Request 1", value = PROD_QUALITY_REQ_EXAMPLE_1,
                                    description = "``dayOffset`` is given"),
                            @ExampleObject(name = "Quality Request 2", value = PROD_QUALITY_REQ_EXAMPLE_2,
                                    description = "``dayOffset`` is *not* given")})})
            @RequestBody @Valid final ProductsByQualityRequest request) {
        final ProductsByQualityResponse response =
                productInventoryService.getProductsByQuality(request);
        return new ResponseEntity<>(response, response.productDtoList().isEmpty()
                ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new product(s)", description = "Create new products in the inventory")
    @ApiResponse(responseCode = "201", description = "Product created successfully",
            content = {@Content(examples = { @ExampleObject(value = CREATE_PROD_RESP_EXAMPLE)})})
    public ProductCreationResponse createProducts(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {@Content(examples = {
                            @ExampleObject(name = "Creation Request 1", value = CREATE_PROD_REQ_EXAMPLE_1,
                                    description = "``quality`` is specified for each new inventory item separately"),
                            @ExampleObject(name = "Creation Request 2", value = CREATE_PROD_REQ_EXAMPLE_2,
                                    description = "``quality`` is specified for all new inventory items with same value")})})
            @RequestBody @Valid final ProductCreationRequest request) {
        return productInventoryService.createProducts(request);
    }

    @DeleteMapping("delete")
    @Operation(summary = "Delete product(s)", description = "Delete products in the inventory")
    @ApiResponse(responseCode = "200", description = "Listed product(s) deleted successfully",
            content = {@Content(schema = @Schema(implementation = ProductDeletionResponse.class))})
    @ApiResponse(responseCode = "404", description = "No products found with any provided ``uuids``",
            content = {@Content(examples = @ExampleObject(DELETE_PROD_EMPTY_ARRAY_RESP_EXAMPLE))})
    public ResponseEntity<ProductDeletionResponse> deleteProductsById(
            @RequestBody @Valid final ProductDeletionRequest request) {
        final ProductDeletionResponse response =
                productInventoryService.deleteProductsById(request);
        return new ResponseEntity<>(response, response.deletedProducts().isEmpty()
                ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }
}
