package com.zmart.api.product.controller.swagger;

import lombok.experimental.UtilityClass;

/*
Using this class for a few more complex examples.
Referencing JSON files is bugged in current Swagger UI version.
*/

@UtilityClass
public final class ApiResponseExamples {

    public static final String CREATE_PROD_RESP_EXAMPLE =
            """
            {
                "data": {
                    "count": 1,
                    "products": [
                        {
                            "itemName": "Moonberries",
                            "itemCode": "MoonBerr",
                            "qualityOperation": 0,
                            "stock": [
                                {
                                    "uuid": "38422dcc-3f1a-4b70-abe8-6bd1222ca68c",
                                    "sellBy": 15,
                                    "quality": 20
                                }
                            ]
                        }
                    ]
                }
            }
            """;

    public static final String CREATE_PROD_REQ_EXAMPLE_1 =
            """
            {
                 "products": [
                     {
                         "itemName": "Moonberries",
                         "itemCode": "MoonBerr",
                         "qualityOperation": 0,
                         "stock": [
                             {
                                 "sellBy": 15,
                                 "quality": 20
                             },
                             {
                                 "sellBy": 20,
                                 "quality": 25
                             }
                         ]
                     }
                 ]
             }
            """;

    public static final String CREATE_PROD_REQ_EXAMPLE_2 =
            """
            {
                 "products": [
                     {
                         "itemName": "Moonberries",
                         "itemCode": "MoonBerr",
                         "quality": 20,
                         "qualityOperation": 0,
                         "stock": [
                             {
                                 "sellBy": 15
                             },
                             {
                                 "sellBy": 20
                             }
                         ]
                     }
                 ]
             }
            """;

    public static final String DELETE_PROD_REQ_EXAMPLE =
            """
            {
                "ids": [
                    "00c12575-c044-4298-acd3-89699f27e7b4",
                    "da558200-b1a4-4cfe-9d08-a51854551023",
                    "589dfadf-c312-43fb-9bc5-8b3039b581c8"
                ]
            }
            """;

    public static final String DELETE_PROD_RESP_EXAMPLE =
            """
            {
             "data": {
                 "deleted": [
                     "00c12575-c044-4298-acd3-89699f27e7b4",
                     "da558200-b1a4-4cfe-9d08-a51854551023"
                 ],
                 "notFound": [
                     "589dfadf-c312-43fb-9bc5-8b3039b581c8"
                 ]
             }
         }
        """;

    public static final String PROD_NOT_FOUND_UUID_RESP_EXAMPLE =
            """
            {
                "timestamp": "2025-02-06T20:52:37.646017411Z",
                "code": "404 NOT_FOUND",
                "exception": "ResponseStatusException",
                "message": "Product with uuid '00000000-0000-0000-0000-000000000000' not found"
            }
            """;

    public static final String PROD_NOT_FOUND_ITEM_NAME_RESP_EXAMPLE =
            """
            {
                "timestamp": "2025-02-06T20:52:37.646017411Z",
                "code": "404 NOT_FOUND",
                "exception": "ResponseStatusException",
                "message": "Product with itemName 'Froot Loops' not found"
            }
            """;

    public static final String PROD_NOT_FOUND_ITEM_CODE_RESP_EXAMPLE =
            """
            {
                "timestamp": "2025-02-06T20:52:37.646017411Z",
                "code": "404 NOT_FOUND",
                "exception": "ResponseStatusException",
                "message": "Product with itemCode 'FrootLoops' not found"
            }
            """;

    public static final String PROD_EMPTY_ARRAY_RESP_EXAMPLE =
            """
            {
                "data": {
                    "count": 0,
                    "products": []
                }
            }
            """;

    public static final String PROD_QUALITY_RESP_EXAMPLE_1 =
            """                     
            {
                 "data": {
                     "count": 2,
                     "products": [
                         {
                             "itemName": "Moonberries",
                             "itemCode": "MoonBerr",
                             "qualityOperation": 0,
                             "stock": [
                                 {
                                     "uuid": "38422dcc-3f1a-4b70-abe8-6bd1222ca68c",
                                     "futureDate": "2077-01-28",
                                     "sellByDate": "2077-02-07",
                                     "stockDate": "2077-01-23",
                                     "currentDate": "2077-01-23",
                                     "sellBy": 10,
                                     "quality": 15
                                 }
                             ]
                         },
                         {
                             "itemName": "Organic Spinach",
                             "itemCode": "OrgSpinach",
                             "qualityOperation": 0,
                             "stock": [
                                 {
                                     "uuid": "862ccf35-bbed-4803-aae0-5fc7e07c4d7f",
                                     "futureDate": "2077-01-28",
                                     "sellByDate": "2077-02-02",
                                     "stockDate": "2077-01-23",
                                     "currentDate": "2077-01-23",
                                     "sellBy": 5,
                                     "quality": 15
                                 }
                             ]
                         }
                     ]
                 }
             }
            """;

    public static final String PROD_QUALITY_RESP_EXAMPLE_2 =
            """
            {
                "data": {
                    "count": 2,
                    "products": [
                        {
                            "itemName": "Moonberries",
                            "itemCode": "MoonBerr",
                            "qualityOperation": 0,
                            "stock": [
                                {
                                    "uuid": "38422dcc-3f1a-4b70-abe8-6bd1222ca68c",
                                    "sellByDate": "2077-02-07",
                                    "stockDate": "2077-01-23",
                                    "currentDate": "2077-01-23",
                                    "sellBy": 15,
                                    "quality": 20
                                }
                            ]
                        },
                        {
                            "itemName": "Organic Spinach",
                            "itemCode": "OrgSpinach",
                            "qualityOperation": 0,
                            "stock": [
                                {
                                    "uuid": "862ccf35-bbed-4803-aae0-5fc7e07c4d7f",
                                    "sellByDate": "2077-02-02",
                                    "stockDate": "2077-01-23",
                                    "currentDate": "2077-01-23",
                                    "sellBy": 10,
                                    "quality": 20
                                }
                            ]
                        }
                    ]
                }
            }
            """;

    public static final String PROD_CODE_AND_NAME_RESP_EXAMPLE_1 =
            """
            {
                 "data": {
                     "product": {
                         "itemName": "Moonberries",
                         "itemCode": "MoonBerr",
                         "qualityOperation": 0,
                         "stock": [
                             {
                                 "uuid": "38422dcc-3f1a-4b70-abe8-6bd1222ca68c",
                                 "futureDate": "2077-01-28",
                                 "sellByDate": "2077-02-07",
                                 "stockDate": "2077-01-23",
                                 "currentDate": "2077-01-23",
                                 "sellBy": 10,
                                 "quality": 15
                             }
                         ]
                     }
                 }
             }
            """;

    public static final String PROD_CODE_AND_NAME_RESP_EXAMPLE_2 =
            """
            {
                "data": {
                    "product": {
                        "itemName": "Moonberries",
                        "itemCode": "MoonBerr",
                        "qualityOperation": 0,
                        "stock": [
                            {
                                "uuid": "38422dcc-3f1a-4b70-abe8-6bd1222ca68c",
                                "sellByDate": "2077-02-07",
                                "stockDate": "2077-01-23",
                                "currentDate": "2077-01-23",
                                "sellBy": 15,
                                "quality": 20
                            }
                        ]
                    }
                }
            }
            """;

    public static final String PROD_ID_RESP_EXAMPLE_1 =
            """
            {
                "data": {
                    "product": {
                        "itemName": "3lb Ground Beef",
                        "itemCode": "3lbGrBeef",
                        "qualityOperation": 2,
                        "stock": [
                            {
                                "uuid": "b660d516-567b-46ed-8dc1-4afd1da2a8a0",
                                "futureDate": "2077-01-28",
                                "sellByDate": "2077-02-07",
                                "stockDate": "2077-01-23",
                                "currentDate": "2077-01-23",
                                "sellBy": 10,
                                "quality": 15
                            }
                        ]
                    }
                }
            }
            """;

    public static final String PROD_ID_RESP_EXAMPLE_2 =
            """
            {
                "data": {
                    "product": {
                        "itemName": "3lb Ground Beef",
                        "itemCode": "3lbGrBeef",
                        "qualityOperation": 2,
                        "stock": [
                            {
                                "uuid": "b660d516-567b-46ed-8dc1-4afd1da2a8a0",
                                "sellByDate": "2077-02-07",
                                "stockDate": "2077-01-23",
                                "currentDate": "2077-01-23",
                                "sellBy": 15,
                                "quality": 10
                            }
                        ]
                    }
                }
            }
            """;

    public static final String PROD_ALL_RESP_EXAMPLE_1 =
            """
            {
                "data": {
                    "count": 8,
                    "products": [
                        {
                            "itemName": "Grannysmith Apple Bunch",
                            "itemCode": "ApplesGran",
                            "qualityOperation": 0,
                            "stock": [
                                {
                                    "uuid": "f50f7d0c-5f1b-440e-9209-0de118b73ae3",
                                    "futureDate": "2077-01-30",
                                    "sellByDate": "2077-01-30",
                                    "stockDate": "2077-01-25",
                                    "currentDate": "2077-01-25",
                                    "sellBy": 0,
                                    "quality": 2
                                },
                                {
                                    "uuid": "8611e2fe-5e9c-42a4-b8c4-783fab1fddb3",
                                    "futureDate": "2077-01-30",
                                    "sellByDate": "2077-01-31",
                                    "stockDate": "2077-01-25",
                                    "currentDate": "2077-01-25",
                                    "sellBy": 1,
                                    "quality": 2
                                },
                                {
                                    "uuid": "34e3e95a-3a88-445c-a0b4-0060c4a38e1d",
                                    "futureDate": "2077-01-30",
                                    "sellByDate": "2077-02-02",
                                    "stockDate": "2077-01-25",
                                    "currentDate": "2077-01-25",
                                    "sellBy": 3,
                                    "quality": 4
                                }
                            ]
                        },
                        {
                            "itemName": "3lb Ground Beef",
                            "itemCode": "3lbGrBeef",
                            "qualityOperation": 2,
                            "stock": [
                                {
                                    "uuid": "cc500abc-9e28-4488-82fe-0868e9fd1d8f",
                                    "futureDate": "2077-01-30",
                                    "sellByDate": "2077-02-09",
                                    "stockDate": "2077-01-25",
                                    "currentDate": "2077-01-25",
                                    "sellBy": 10,
                                    "quality": 15
                                }
                            ]
                        },
                        {
                            "itemName": "Corn on the cob",
                            "itemCode": "CornCob",
                            "qualityOperation": 1,
                            "stock": [
                                {
                                    "uuid": "6233a927-7182-4ff8-8321-61afdc4f4b94",
                                    "futureDate": "2077-01-30",
                                    "sellByDate": "2077-02-04",
                                    "stockDate": "2077-01-25",
                                    "currentDate": "2077-01-25",
                                    "sellBy": 5,
                                    "quality": 17
                                }
                            ]
                        },
                        {
                            "itemName": "Organic Spinach",
                            "itemCode": "OrgSpinach",
                            "qualityOperation": 0,
                            "stock": [
                                {
                                    "uuid": "7bf252ec-7c04-47f3-a89a-e442f1fa094e",
                                    "futureDate": "2077-01-30",
                                    "sellByDate": "2077-02-04",
                                    "stockDate": "2077-01-25",
                                    "currentDate": "2077-01-25",
                                    "sellBy": 5,
                                    "quality": 15
                                }
                            ]
                        },
                        {
                            "itemName": "Moonberries",
                            "itemCode": "MoonBerr",
                            "qualityOperation": 0,
                            "stock": [
                                {
                                    "uuid": "622c772d-35ee-4b60-a3fd-1635cec5d9a6",
                                    "futureDate": "2077-01-30",
                                    "sellByDate": "2077-02-09",
                                    "stockDate": "2077-01-25",
                                    "currentDate": "2077-01-25",
                                    "sellBy": 10,
                                    "quality": 15
                                }
                            ]
                        },
                        {
                            "itemName": "Twinkies",
                            "itemCode": "Twinkies",
                            "qualityOperation": 3,
                            "stock": [
                                {
                                    "uuid": "0743d028-4c43-402d-91c0-2607a3a98999",
                                    "futureDate": "2077-01-30",
                                    "sellByDate": "2077-01-26",
                                    "stockDate": "2077-01-25",
                                    "currentDate": "2077-01-25",
                                    "sellBy": -4,
                                    "quality": 50
                                }
                            ]
                        }
                    ]
                }
            }
            """;

    public static final String PROD_ALL_RESP_EXAMPLE_2 =
            """
            {
                 "data": {
                     "count": 8,
                     "products": [
                         {
                             "itemName": "Grannysmith Apple Bunch",
                             "itemCode": "ApplesGran",
                             "qualityOperation": 0,
                             "stock": [
                                 {
                                     "uuid": "f50f7d0c-5f1b-440e-9209-0de118b73ae3",
                                     "sellByDate": "2077-01-30",
                                     "stockDate": "2077-01-25",
                                     "currentDate": "2077-01-25",
                                     "sellBy": 5,
                                     "quality": 7
                                 },
                                 {
                                     "uuid": "8611e2fe-5e9c-42a4-b8c4-783fab1fddb3",
                                     "sellByDate": "2077-01-31",
                                     "stockDate": "2077-01-25",
                                     "currentDate": "2077-01-25",
                                     "sellBy": 6,
                                     "quality": 7
                                 },
                                 {
                                     "uuid": "34e3e95a-3a88-445c-a0b4-0060c4a38e1d",
                                     "sellByDate": "2077-02-02",
                                     "stockDate": "2077-01-25",
                                     "currentDate": "2077-01-25",
                                     "sellBy": 8,
                                     "quality": 9
                                 }
                             ]
                         },
                         {
                             "itemName": "3lb Ground Beef",
                             "itemCode": "3lbGrBeef",
                             "qualityOperation": 2,
                             "stock": [
                                 {
                                     "uuid": "cc500abc-9e28-4488-82fe-0868e9fd1d8f",
                                     "sellByDate": "2077-02-09",
                                     "stockDate": "2077-01-25",
                                     "currentDate": "2077-01-25",
                                     "sellBy": 15,
                                     "quality": 10
                                 }
                             ]
                         },
                         {
                             "itemName": "Corn on the cob",
                             "itemCode": "CornCob",
                             "qualityOperation": 1,
                             "stock": [
                                 {
                                     "uuid": "6233a927-7182-4ff8-8321-61afdc4f4b94",
                                     "sellByDate": "2077-02-04",
                                     "stockDate": "2077-01-25",
                                     "currentDate": "2077-01-25",
                                     "sellBy": 10,
                                     "quality": 12
                                 }
                             ]
                         },
                         {
                             "itemName": "Organic Spinach",
                             "itemCode": "OrgSpinach",
                             "qualityOperation": 0,
                             "stock": [
                                 {
                                     "uuid": "7bf252ec-7c04-47f3-a89a-e442f1fa094e",
                                     "sellByDate": "2077-02-04",
                                     "stockDate": "2077-01-25",
                                     "currentDate": "2077-01-25",
                                     "sellBy": 10,
                                     "quality": 20
                                 }
                             ]
                         },
                         {
                             "itemName": "Moonberries",
                             "itemCode": "MoonBerr",
                             "qualityOperation": 0,
                             "stock": [
                                 {
                                     "uuid": "622c772d-35ee-4b60-a3fd-1635cec5d9a6",
                                     "sellByDate": "2077-02-09",
                                     "stockDate": "2077-01-25",
                                     "currentDate": "2077-01-25",
                                     "sellBy": 15,
                                     "quality": 20
                                 }
                             ]
                         },
                         {
                             "itemName": "Twinkies",
                             "itemCode": "Twinkies",
                             "qualityOperation": 3,
                             "stock": [
                                 {
                                     "uuid": "0743d028-4c43-402d-91c0-2607a3a98999",
                                     "sellByDate": "2077-01-26",
                                     "stockDate": "2077-01-25",
                                     "currentDate": "2077-01-25",
                                     "sellBy": 1,
                                     "quality": 50
                                 }
                             ]
                         }
                     ]
                 }
             }
            """;

    public static final String PROD_ALL_RESP_EXAMPLE_3 =
            """
            {
              "data": {
                  "count": 8,
                  "products": [
                      {
                          "itemName": "3lb Ground Beef",
                          "itemCode": "3lbGrBeef",
                          "qualityOperation": 2,
                          "stock": [
                              {
                                  "uuid": "b683f6ff-11e6-46bc-b15d-5032255814f6",
                                  "sellByDate": "2077-02-09",
                                  "stockDate": "2077-01-25",
                                  "currentDate": "2077-01-25",
                                  "sellBy": 15,
                                  "quality": 10
                              }
                          ]
                      },
                      {
                          "itemName": "Corn on the cob",
                          "itemCode": "CornCob",
                          "qualityOperation": 1,
                          "stock": [
                              {
                                  "uuid": "38def422-c804-46c3-b799-e7a300264ef6",
                                  "sellByDate": "2077-02-04",
                                  "stockDate": "2077-01-25",
                                  "currentDate": "2077-01-25",
                                  "sellBy": 10,
                                  "quality": 12
                              }
                          ]
                      },
                      {
                          "itemName": "Grannysmith Apple Bunch",
                          "itemCode": "ApplesGran",
                          "qualityOperation": 0,
                          "stock": [
                              {
                                  "uuid": "a0c914b7-7742-41dc-ac7f-d4584091ffdc",
                                  "sellByDate": "2077-01-30",
                                  "stockDate": "2077-01-25",
                                  "currentDate": "2077-01-25",
                                  "sellBy": 5,
                                  "quality": 7
                              },
                              {
                                  "uuid": "be652a99-b772-40b8-95d3-78688b0c4eb2",
                                  "sellByDate": "2077-01-31",
                                  "stockDate": "2077-01-25",
                                  "currentDate": "2077-01-25",
                                  "sellBy": 6,
                                  "quality": 7
                              },
                              {
                                  "uuid": "518288d0-e8f1-4ab8-8d66-ba647f6fc30e",
                                  "sellByDate": "2077-02-02",
                                  "stockDate": "2077-01-25",
                                  "currentDate": "2077-01-25",
                                  "sellBy": 8,
                                  "quality": 9
                              }
                          ]
                      },
                      {
                          "itemName": "Moonberries",
                          "itemCode": "MoonBerr",
                          "qualityOperation": 0,
                          "stock": [
                              {
                                  "uuid": "837efb82-2337-4552-a277-c615577232ae",
                                  "sellByDate": "2077-02-09",
                                  "stockDate": "2077-01-25",
                                  "currentDate": "2077-01-25",
                                  "sellBy": 15,
                                  "quality": 20
                              }
                          ]
                      },
                      {
                          "itemName": "Organic Spinach",
                          "itemCode": "OrgSpinach",
                          "qualityOperation": 0,
                          "stock": [
                              {
                                  "uuid": "3efaa7da-a106-4de7-adaf-9211d74dafb6",
                                  "sellByDate": "2077-02-04",
                                  "stockDate": "2077-01-25",
                                  "currentDate": "2077-01-25",
                                  "sellBy": 10,
                                  "quality": 20
                              }
                          ]
                      },
                      {
                          "itemName": "Twinkies",
                          "itemCode": "Twinkies",
                          "qualityOperation": 3,
                          "stock": [
                              {
                                  "uuid": "df4f1e2c-dab9-4953-9bcf-7aa07f296fb4",
                                  "sellByDate": "2077-01-26",
                                  "stockDate": "2077-01-25",
                                  "currentDate": "2077-01-25",
                                  "sellBy": 1,
                                  "quality": 50
                              }
                          ]
                      }
                  ]
              }
            }
            """;

    public static final String BAD_REQUEST_EXAMPLE_MISSING_PARAM =
            """
            {
                "timestamp": "2025-02-11T22:16:30.598Z",
                "code": "400 BAD_REQUEST",
                "exception": "MissingServletRequestParameterException",
                "message": "Required request parameter '<paramName>' for method parameter type <Type> is not present"
            }
            """;

    public static final String BAD_REQUEST_EXAMPLE_VALIDATION_SORT_BY =
            """
            {
                "timestamp": "2025-02-11T22:16:30.598Z",
                "code": "400 BAD_REQUEST",
                "exception": "MethodArgumentNotValidException",
                "message": "Property sortBy must match sellBy or itemName or quality or stockDate",
                "cause": {
                    "message": "Validation failed for argument [1] in... default message [must match sellBy or itemName or quality or stockDate]]"
                }
            }
            """;

    public static final String BAD_REQUEST_EXAMPLE_VALIDATION_SORT_ORDER =
            """
            {
                "timestamp": "2025-02-11T22:16:30.598Z",
                "code": "400 BAD_REQUEST",
                "exception": "MethodArgumentNotValidException",
                "message": "Property sortOrder must match ASC or DESC",
                "cause": {
                    "message": "Validation failed for argument [1] in... default message [must match ASC or DESC]]"
                }
            }
            """;

    public static final String BAD_REQUEST_EXAMPLE_VALIDATION_LIMIT =
            """
            {
                "timestamp": "2025-02-11T22:16:30.598Z",
                "code": "400 BAD_REQUEST",
                "exception": "MethodArgumentNotValidException",
                "message": "Invalid properties: limit must be greater than or equal to 1, provided 0",
                "cause": {
                    "message": "Validation failed for argument [1] in... default message [must be greater than or equal to 1, provided 0]]"
                }
            }
            """;

    public static final String BAD_REQUEST_EXAMPLE_METHOD_NOT_ALLOWED =
            """
            {
                "timestamp": "2025-02-11T22:16:30.598Z",
                "code": "405 METHOD_NOT_ALLOWED",
                "exception": "HttpRequestMethodNotSupportedException",
                "message": "Request method '<httpVerb>' is not supported"
            }
            """;

    public static final String BAD_REQUEST_EXAMPLE_METHOD_ARG_TYPE_MISMATCH =
            """
            {
             "timestamp": "2025-02-11T22:16:30.598Z",
             "code": "400 BAD_REQUEST",
             "exception": "MethodArgumentTypeMismatchException",
             "message": "Failed to convert value of type '<Type>' to required type '<Type>'; For input <type>: <arg>"
            }
            """;

    public static final String BAD_REQUEST_MALFORMED_UUID_GET_BY_ID_EXAMPLE =
            """
            {
                "timestamp": "2025-02-12T00:02:44.271Z",
                "code": "400 BAD_REQUEST",
                "exception": "MethodArgumentTypeMismatchException",
                "message": "Failed to convert value of type 'java.lang.String' to required type 'java.util.UUID'; Invalid UUID string: abcd-1234"
            }
            """;
    
    public static final String BAD_REQUEST_MALFORMED_UUID_DELETE_EXAMPLE =
            """
            {
                "timestamp": "2025-02-11T23:40:54.105Z",
                "code": "400 BAD_REQUEST",
                "exception": "HttpMessageNotReadableException",
                "cause": {
                    "message": "JSON parse error: Cannot deserialize... UUID has to be represented by standard 36-char representation"
                }
            }
            """;

    public static final String BAD_REQUEST_INVALID_PROPS_CREATE_EXAMPLE =
            """
            {
                "code": "400 BAD_REQUEST",
                "message": "Invalid properties: productList[0].inventoryList[0].sellBy must not be null, productList[0].inventoryList[0].quality must not be null",
                "exception": "MethodArgumentNotValidException",
                "cause": {
                    "message": "Validation failed for argument [0] in.... with 2 errors: 'productCreateOrUpdateRequest' on field 'productList[0].inventoryList[0].sellBy'...."
                }
            }
            """;
}
