package com.zmart.api.docs.product;

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
import com.zmart.api.product.validation.ValidAlpha;
import com.zmart.api.product.validation.ValidAlphaWithSpace;
import com.zmart.api.product.validation.ValidPositiveNumeral;
import com.zmart.api.product.validation.ValidQuality;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

import static com.zmart.api.docs.product.ApiResponseDescriptions.*;
import static com.zmart.api.docs.product.ApiResponseExamples.*;
import static com.zmart.api.product.util.ProductConstants.UUID_DUMMY;

@Tag(name = "Product", description = "Product management APIs")
public interface ProductRestControllerDocumentation {

    @GetMapping("all")
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
                            """ + FUTURE_DATE_NOT_GIVEN_GET_ALL_DESC),
                    @ExampleObject(name = "No products found", value = PROD_EMPTY_ARRAY_RESP_EXAMPLE)})})
    @ApiResponse(responseCode = "400", description = "Bad Request - A required parameter is missing or validation failed",
            content = {@Content(examples = {
                    @ExampleObject(name = "Missing Required Parameter", value = BAD_REQUEST_EXAMPLE_MISSING_PARAM,
                            description = BAD_REQUEST_MISSING_PARAM_DESCRIPTION),
                    @ExampleObject(name = "Method Not Allowed", value = BAD_REQUEST_EXAMPLE_METHOD_NOT_ALLOWED,
                            description = BAD_REQUEST_METHOD_NOT_ALLOWED_DESCRIPTION),
                    @ExampleObject(name = "Type Mismatch", value = BAD_REQUEST_EXAMPLE_METHOD_ARG_TYPE_MISMATCH,
                            description = BAD_REQUEST_METHOD_ARG_TYPE_MISMATCH_DESCRIPTION),
                    @ExampleObject(name = "Validation Error - sortBy", value = BAD_REQUEST_EXAMPLE_VALIDATION_SORT_BY,
                            description = BAD_REQUEST_VALIDATION_DESCRIPTION),
                    @ExampleObject(name = "Validation Error - sortOrder", value = BAD_REQUEST_EXAMPLE_VALIDATION_SORT_ORDER,
                            description = BAD_REQUEST_VALIDATION_DESCRIPTION),
                    @ExampleObject(name = "Validation Error - limit", value = BAD_REQUEST_EXAMPLE_VALIDATION_LIMIT,
                            description = BAD_REQUEST_VALIDATION_DESCRIPTION)})})
    ResponseEntity<ProductAllProdsResponse> getAllProducts(
        @Valid @ParameterObject ProductQueryParamsDto request);

    @GetMapping("id")
    @Operation(summary = "Get product by id", description = "Get product matching given ``uuid``"
            + "&nbsp;and calculate values & dates based on days to offset (``dayOffset``) & ``qualityOperation``.")
    @ApiResponse(responseCode = "200", description = "Product with matching ``uuid`` found",
            content = {@Content(examples = {
                    @ExampleObject(name = "Id Response 1", value = PROD_ID_RESP_EXAMPLE_1,
                            description = """
                            Retrieves product with the given ``uuid`` and offsets the current date by 5 days.
                            """ + FUTURE_DATE_5_GIVEN_QUALITY_OP_0_DESC),
                    @ExampleObject(name = "Id Response 2", value = PROD_ID_RESP_EXAMPLE_2,
                            description = """
                            Retrieves product with the given ``uuid`` and does *not* offset the current date.
                            """ + FUTURE_DATE_NOT_GIVEN_DESC)})})
    @ApiResponse(responseCode = "400", description = "Bad Request - A required parameter is missing or validation failed",
            content = {@Content(examples = {
                    @ExampleObject(name = "Malformed UUID", value = BAD_REQUEST_MALFORMED_UUID_GET_BY_ID_EXAMPLE,
                            description = BAD_REQUEST_MALFORMED_UUID_GET_BY_ID_DESCRIPTION),
                    @ExampleObject(name = "Missing Required Parameter", value = BAD_REQUEST_EXAMPLE_MISSING_PARAM,
                            description = BAD_REQUEST_MISSING_PARAM_DESCRIPTION),
                    @ExampleObject(name = "Method Not Allowed", value = BAD_REQUEST_EXAMPLE_METHOD_NOT_ALLOWED,
                            description = BAD_REQUEST_METHOD_NOT_ALLOWED_DESCRIPTION),
                    @ExampleObject(name = "Type Mismatch", value = BAD_REQUEST_EXAMPLE_METHOD_ARG_TYPE_MISMATCH,
                            description = BAD_REQUEST_METHOD_ARG_TYPE_MISMATCH_DESCRIPTION),
                    @ExampleObject(name = "Validation Error - sortBy", value = BAD_REQUEST_EXAMPLE_VALIDATION_SORT_BY,
                            description = BAD_REQUEST_VALIDATION_DESCRIPTION),
                    @ExampleObject(name = "Validation Error - sortOrder", value = BAD_REQUEST_EXAMPLE_VALIDATION_SORT_ORDER,
                            description = BAD_REQUEST_VALIDATION_DESCRIPTION),
                    @ExampleObject(name = "Validation Error - limit", value = BAD_REQUEST_EXAMPLE_VALIDATION_LIMIT,
                            description = BAD_REQUEST_VALIDATION_DESCRIPTION)})})
    @ApiResponse(responseCode = "404", description = "No product found with matching ``uuid``",
            content = {@Content(examples = @ExampleObject(PROD_NOT_FOUND_UUID_RESP_EXAMPLE))})
    ResponseEntity<ProductByIdResponse> getProductById(
        @NotNull @RequestParam
        @Parameter(description = "The UUID of the item", example = UUID_DUMMY) final UUID uuid,
        @ValidPositiveNumeral @RequestParam(defaultValue = "0") final Integer dayOffset);

    @GetMapping("item-name")
    @Operation(summary = "Get product by itemName", description = "Get product matching given ``itemName``"
            + SORT_AND_OFFSET_AND_QUERY_DESC + SWAGGER_QUALITY_OP_RENDER)
    @ApiResponse(responseCode = "200", description = "Product with matching ``itemName`` found",
            content = {@Content(examples = {
                    @ExampleObject(name = "ItemName Response 1", value = PROD_CODE_AND_NAME_RESP_EXAMPLE_1,
                            description = """
                            Retrieves product with the given ``itemName`` and offsets the current date by 5 days.
                            """ + FUTURE_DATE_5_GIVEN_QUALITY_OP_0_DESC),
                    @ExampleObject(name = "ItemName Response 2", value = PROD_CODE_AND_NAME_RESP_EXAMPLE_2,
                            description = """
                            Retrieves product with the given ``itemName`` and does *not* offset the current date.
                            """ + FUTURE_DATE_NOT_GIVEN_DESC)})})
    @ApiResponse(responseCode = "400", description = "Bad Request - A required parameter is missing or validation failed",
            content = {@Content(examples = {
                    @ExampleObject(name = "Missing Required Parameter", value = BAD_REQUEST_EXAMPLE_MISSING_PARAM,
                            description = BAD_REQUEST_MISSING_PARAM_DESCRIPTION),
                    @ExampleObject(name = "Method Not Allowed", value = BAD_REQUEST_EXAMPLE_METHOD_NOT_ALLOWED,
                            description = BAD_REQUEST_METHOD_NOT_ALLOWED_DESCRIPTION),
                    @ExampleObject(name = "Type Mismatch", value = BAD_REQUEST_EXAMPLE_METHOD_ARG_TYPE_MISMATCH,
                            description = BAD_REQUEST_METHOD_ARG_TYPE_MISMATCH_DESCRIPTION),
                    @ExampleObject(name = "Validation Error - sortBy", value = BAD_REQUEST_EXAMPLE_VALIDATION_SORT_BY,
                            description = BAD_REQUEST_VALIDATION_DESCRIPTION),
                    @ExampleObject(name = "Validation Error - sortOrder", value = BAD_REQUEST_EXAMPLE_VALIDATION_SORT_ORDER,
                            description = BAD_REQUEST_VALIDATION_DESCRIPTION),
                    @ExampleObject(name = "Validation Error - limit", value = BAD_REQUEST_EXAMPLE_VALIDATION_LIMIT,
                            description = BAD_REQUEST_VALIDATION_DESCRIPTION)})})
    @ApiResponse(responseCode = "404", description = "No product found with matching ``itemName``",
            content = {@Content(examples = @ExampleObject(PROD_NOT_FOUND_ITEM_NAME_RESP_EXAMPLE))})
    ResponseEntity<ProductByItemNameResponse> getProductByItemName(
        @ValidAlphaWithSpace @RequestParam
        @Parameter(description = "The name of the item", example = "Moonberries") final String itemName,
        @Valid @ParameterObject final ProductQueryParamsDto request);

    @GetMapping("item-code")
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
    @ApiResponse(responseCode = "400", description = "Bad Request - A required parameter is missing or validation failed",
            content = {@Content(examples = {
                    @ExampleObject(name = "Missing Required Parameter", value = BAD_REQUEST_EXAMPLE_MISSING_PARAM,
                            description = BAD_REQUEST_MISSING_PARAM_DESCRIPTION),
                    @ExampleObject(name = "Method Not Allowed", value = BAD_REQUEST_EXAMPLE_METHOD_NOT_ALLOWED,
                            description = BAD_REQUEST_METHOD_NOT_ALLOWED_DESCRIPTION),
                    @ExampleObject(name = "Type Mismatch", value = BAD_REQUEST_EXAMPLE_METHOD_ARG_TYPE_MISMATCH,
                            description = BAD_REQUEST_METHOD_ARG_TYPE_MISMATCH_DESCRIPTION),
                    @ExampleObject(name = "Validation Error - sortBy", value = BAD_REQUEST_EXAMPLE_VALIDATION_SORT_BY,
                            description = BAD_REQUEST_VALIDATION_DESCRIPTION),
                    @ExampleObject(name = "Validation Error - sortOrder", value = BAD_REQUEST_EXAMPLE_VALIDATION_SORT_ORDER,
                            description = BAD_REQUEST_VALIDATION_DESCRIPTION),
                    @ExampleObject(name = "Validation Error - limit", value = BAD_REQUEST_EXAMPLE_VALIDATION_LIMIT,
                            description = BAD_REQUEST_VALIDATION_DESCRIPTION)})})
    @ApiResponse(responseCode = "404", description = "No product found with matching ``itemCode``",
            content = {@Content(examples = @ExampleObject(PROD_NOT_FOUND_ITEM_CODE_RESP_EXAMPLE))})
    ResponseEntity<ProductByItemCodeResponse> getProductByItemCode(
        @ValidAlpha @RequestParam
        @Parameter(description = "The code of the item", example = "MoonBerr") final String itemCode,
        @Valid @ParameterObject final ProductQueryParamsDto request);

    @GetMapping("quality")
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
                            """ + FUTURE_DATE_NOT_GIVEN_DESC),
                    @ExampleObject(name = "No Products Found", value = PROD_EMPTY_ARRAY_RESP_EXAMPLE)})})
    @ApiResponse(responseCode = "400", description = "Bad Request - A required parameter is missing or validation failed",
            content = {@Content(examples = {
                    @ExampleObject(name = "Missing Required Parameter", value = BAD_REQUEST_EXAMPLE_MISSING_PARAM,
                            description = BAD_REQUEST_MISSING_PARAM_DESCRIPTION),
                    @ExampleObject(name = "Method Not Allowed", value = BAD_REQUEST_EXAMPLE_METHOD_NOT_ALLOWED,
                            description = BAD_REQUEST_METHOD_NOT_ALLOWED_DESCRIPTION),
                    @ExampleObject(name = "Type Mismatch", value = BAD_REQUEST_EXAMPLE_METHOD_ARG_TYPE_MISMATCH,
                            description = BAD_REQUEST_METHOD_ARG_TYPE_MISMATCH_DESCRIPTION),
                    @ExampleObject(name = "Validation Error - sortBy", value = BAD_REQUEST_EXAMPLE_VALIDATION_SORT_BY,
                            description = BAD_REQUEST_VALIDATION_DESCRIPTION),
                    @ExampleObject(name = "Validation Error - sortOrder", value = BAD_REQUEST_EXAMPLE_VALIDATION_SORT_ORDER,
                            description = BAD_REQUEST_VALIDATION_DESCRIPTION),
                    @ExampleObject(name = "Validation Error - limit", value = BAD_REQUEST_EXAMPLE_VALIDATION_LIMIT,
                            description = BAD_REQUEST_VALIDATION_DESCRIPTION)})})
    ResponseEntity<ProductsByQualityResponse> getProductsByQuality(
        @ValidQuality @RequestParam
        @Parameter(description = "The quality of the item", example = "20") final Integer quality,
        @Valid @ParameterObject final ProductQueryParamsDto request);

    @PostMapping(value = "create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new product(s)", description = "Create new products in the inventory")
    @ApiResponse(responseCode = "201", description = "Product created successfully",
            content = {@Content(examples = { @ExampleObject(value = CREATE_PROD_RESP_EXAMPLE)})})
    @ApiResponse(responseCode = "400", description = "Bad Request - Method Argument Not Valid",
            content = {@Content(examples = {
                    @ExampleObject(name = "Invalid Properties", value = BAD_REQUEST_INVALID_PROPS_CREATE_EXAMPLE,
                            description = BAD_REQUEST_INVALID_PROPS_CREATE_DESCRIPTION),
                    @ExampleObject(name = "Method Not Allowed", value = BAD_REQUEST_EXAMPLE_METHOD_NOT_ALLOWED,
                            description = BAD_REQUEST_METHOD_NOT_ALLOWED_DESCRIPTION),
                    @ExampleObject(name = "Missing Quality Property", value = BAD_REQUEST_NULL_QUALITY_PARENT_AND_CHILD_CREATE_EXAMPLE,
                            description = BAD_REQUEST_NULL_QUALITY_PARENT_AND_CHILD_CREATE_DESCRIPTION)})})
    ResponseEntity<ProductCreateResponse> createProducts(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
                content = {@Content(examples = {
                        @ExampleObject(name = "Creation Request 1", value = CREATE_PROD_REQ_EXAMPLE_1,
                                description = "``quality`` is specified for each new inventory item separately"),
                        @ExampleObject(name = "Creation Request 2", value = CREATE_PROD_REQ_EXAMPLE_2,
                                description = "``quality`` is specified for all new inventory items with same value")})})
        @RequestBody @Valid final ProductCreateRequest request);

    @DeleteMapping(value = "delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete product(s)", description = "Delete products in the inventory")
    @ApiResponse(responseCode = "200", description = "Listed product(s) deleted successfully",
            content = {@Content(examples = @ExampleObject(DELETE_PROD_RESP_EXAMPLE))})
    @ApiResponse(responseCode = "400", description = "Bad Request - Http Message Not Readable",
            content = {@Content(examples = {
                    @ExampleObject(name = "Malformed UUID", value = BAD_REQUEST_MALFORMED_UUID_DELETE_EXAMPLE,
                            description = BAD_REQUEST_MALFORMED_UUID_DELETE_DESCRIPTION),
                    @ExampleObject(name = "Method Not Allowed", value = BAD_REQUEST_EXAMPLE_METHOD_NOT_ALLOWED,
                            description = BAD_REQUEST_METHOD_NOT_ALLOWED_DESCRIPTION)})})
    ResponseEntity<ProductDeleteResponse> deleteProductsById(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Deletion Request",
                content = @Content(examples = @ExampleObject(value = DELETE_PROD_REQ_EXAMPLE)))
        @RequestBody @Valid final ProductDeleteRequest request);

}
