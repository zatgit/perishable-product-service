package com.zmart.api.product.util;

import lombok.AllArgsConstructor;

import static com.zmart.api.product.controller.swagger.SwaggerProdDescriptionConstants.QUALITY_OP_0_DESC;
import static com.zmart.api.product.controller.swagger.SwaggerProdDescriptionConstants.QUALITY_OP_1_DESC;
import static com.zmart.api.product.controller.swagger.SwaggerProdDescriptionConstants.QUALITY_OP_2_DESC;
import static com.zmart.api.product.controller.swagger.SwaggerProdDescriptionConstants.QUALITY_OP_3_DESC;

@AllArgsConstructor
public enum QualityOperationEnum {
    QUALITY_OPERATION_0(QUALITY_OP_0_DESC),
    QUALITY_OPERATION_1(QUALITY_OP_1_DESC),
    QUALITY_OPERATION_2(QUALITY_OP_2_DESC),
    QUALITY_OPERATION_3(QUALITY_OP_3_DESC);

    private String caseDescription;
}
