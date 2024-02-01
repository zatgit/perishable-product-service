package com.zmart.api.product.cache.controller.swagger;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class SwaggerCacheExampleConstants {

    public static final String CREATE_AND_GET_CACHE_KVP_REQ_AND_RESP_SUCCESS_EXAMPLE =
            """
            { "products::ProductInventoryServiceImpl,getAllProducts,100,sellBy,DESC,0": "<test value>" }
            """;

    public static final String CACHE_RESP_KEY_NOT_FOUND_EXAMPLE =
            """
            { "message": "Key not found" }
            """;

    public static final String GET_ALL_CACHE_KEYS_RESP_SUCCESS_EXAMPLE =
            """
            {
                "keys": [
                    "products::ProductInventoryServiceImpl,getAllProducts,100,sellBy,DESC,0",
                    "products::ProductInventoryServiceImpl,getProductByItemName,Moonberries,100,sellBy,DESC,0"
                ]
            }   
            """;

    public static final String GET_ALL_CACHE_KEYS_RESP_NOT_FOUND_EXAMPLE =
            """
            { "keys": [] }        
            """;

    public static final String EVICT_CACHE_RESP_SUCCESS_EXAMPLE =
            """
            { "message": "Cache refreshed successfully" }
            """;
}
