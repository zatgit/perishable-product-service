package com.zmart.api.product.validation.validator;

import com.zmart.api.product.dto.InventoryDto;
import com.zmart.api.product.dto.ProductCreationDto;
import com.zmart.api.product.dto.request.ProductCreationRequest;
import com.zmart.api.product.validation.ValidCreateProductQualities;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static com.zmart.api.product.util.ProductConstants.PRODUCT_LIST;

public class CreateProductQualitiesValidator implements ConstraintValidator<ValidCreateProductQualities, ProductCreationRequest> {

    @Override
    public boolean isValid(final ProductCreationRequest request, final ConstraintValidatorContext ctx) {
        ctx.disableDefaultConstraintViolation();
        //Request obj quality value is null & 1+ InventoryDto objs null
        final List<String> filteredQualityBothNull = request.productCreationDtoList().stream()
                .filter(p -> p.quality() == null
                        && hasNullQualityInAnyInventory(p))
                .map(p -> p.itemName())
                .toList();
        //Get productList indices of violating object & create message
        final List<String> messageList = IntStream.range(0, request.productCreationDtoList().size())
                .filter(p -> filteredQualityBothNull.contains(request.productCreationDtoList().get(p).itemName()))
                .boxed()
                .map(i ->
                        String.format("%s%s%s %s", PRODUCT_LIST, i, ".quality",
                                "must not be simultaneously null in productDto object & its child stock items"))
                .toList();
        ctx.buildConstraintViolationWithTemplate(String.valueOf(messageList))
                .addConstraintViolation();
        return hasNoViolations(filteredQualityBothNull);
    }

    private boolean hasNullQualityInAnyInventory(ProductCreationDto dto) {
        final Optional<List<InventoryDto>> optionalInventoryList =
                Optional.ofNullable(dto.inventoryDtoList());
        return optionalInventoryList
                .map(o -> o.stream().anyMatch(i -> i.quality() == null))
                .orElse(true);
    }

    private boolean hasNoViolations(final List<String> violationList) {
        if(violationList != null) {
            return violationList.stream().count() < 1;
        }
        return true;
    }
}