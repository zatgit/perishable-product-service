package com.zmart.api.product.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class ProductConstants {

    public static final String API_VERSION = "2";
    public static final String API_BASE_RESOURCE_PATH = "/api/v" + API_VERSION + "/";
    public static final String GPL3_URL =
            "https://www.gnu.org/licenses/gpl-3.0.en.html?ref=itsfoss.com#license-text";
    public static final String API_DESCRIPTION = """
            A RESTful API for tracking a grocery store's inventory of 
            perishable goods developed with Java and Spring.""";
    public static final String PRODUCT = "product";
    public static final String PRODUCTS = "products";
    public static final String STOCK = "stock";
    public static final String DATA = "data";
    public static final String PRODUCT_QUERY_PARAMS_DTO = "queryParams";
    public static final String EMPTY_STRING = "";
    public static final String UUID = "uuid";
    public static final String UUID_DUMMY = "00000000-0000-0000-0000-000000000000";
    public static final String SELL_BY = "sellBy";
    public static final String ITEM_NAME = "itemName";
    public static final String ITEM_CODE = "itemCode";
    public static final String QUALITY = "quality";
    public static final String STOCK_DATE = "stockDate";
    public static final String PRODUCT_LIST = "productList";
    public static final String INVENTORY_LIST = "inventoryList";
}
