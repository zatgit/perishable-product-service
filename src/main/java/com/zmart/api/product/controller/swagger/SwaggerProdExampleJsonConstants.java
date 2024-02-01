package com.zmart.api.product.controller.swagger;

import lombok.experimental.UtilityClass;

/*
Using this class for a few more complex examples.
Referencing JSON files is bugged in current Swagger UI version.
*/

@UtilityClass
public final class SwaggerProdExampleJsonConstants {

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

    public static final String DELETE_PROD_EMPTY_ARRAY_RESP_EXAMPLE =
            """
            {
                "data": {
                    "deleted": []
                }
            }
            """;

    public static final String PROD_EMPTY_OBJ_RESP_NOT_FOUND =
            """
            {
                "data": {
                    "product": {}
                }
            }
            """;

    public static final String PROD_EMPTY_ARRAY_RESP_NOT_FOUND =
            """
            {
                "data": {
                    "count": 0,
                    "products": []
                }
            }
            """;

    public static final String PROD_QUALITY_REQ_EXAMPLE_1 =
            """
            {
              "quality": 20,
                  "dataView": {
                    "dayOffset": 5,
                    "limit": 100,
                    "sortBy": "sellBy",
                    "sortOrder": "DESC"
                    }
            }
            """;

    public static final String PROD_QUALITY_REQ_EXAMPLE_2 =
            """
            {
              "quality": 20,
                  "dataView": {
                    "limit": 100,
                    "sortBy": "sellBy",
                    "sortOrder": "DESC"
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

    public static final String PROD_ITEM_NAME_REQ_EXAMPLE_1 =
            """
            {
                "itemName": "Moonberries",
                "dataView": {
                    "dayOffset": 5,
                    "limit": 100,
                    "sortBy": "sellBy",
                    "sortOrder": "DESC"
                }
            }
            """;

    public static final String PROD_ITEM_NAME_REQ_EXAMPLE_2 =
            """
            {
                "itemCode": "MoonBerr",
                "dataView": {
                    "limit": 100,
                    "sortBy": "sellBy",
                    "sortOrder": "DESC"
                }
            }
            """;

    public static final String PROD_ITEM_CODE_REQ_EXAMPLE_1 =
            """
            {
                "itemCode": "MoonBerr",
                "dataView": {
                    "dayOffset": 5,
                    "limit": 100,
                    "sortBy": "sellBy",
                    "sortOrder": "DESC"
                }
            }
            """;

    public static final String PROD_ITEM_CODE_REQ_EXAMPLE_2 =
            """
            {
                "itemCode": "MoonBerr",
                "dataView": {
                    "limit": 100,
                    "sortBy": "sellBy",
                    "sortOrder": "DESC"
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

    public static final String PROD_ID_REQ_EXAMPLE_1 =
            """
            { "uuid": "b660d516-567b-46ed-8dc1-4afd1da2a8a0", "dayOffset": 5 }
            """;

    public static final String PROD_ID_REQ_EXAMPLE_2 =
            """
            { "uuid": "b660d516-567b-46ed-8dc1-4afd1da2a8a0" }
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

    public static final String PROD_ALL_REQ_EXAMPLE_1 =
            """
            {
                "dataView": {
                    "dayOffset": 5,
                    "limit": 100,
                    "sortBy": "quality",
                    "sortOrder": "ASC"
                }
            }
            """;

    public static final String PROD_ALL_REQ_EXAMPLE_2 =
            """
            {
                "dataView": {
                    "limit": 100,
                    "sortBy": "quality",
                    "sortOrder": "ASC"
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

    public static final String PROD_ALL_RESP_NOT_FOUND =
            """
            {
                "data": {
                    "count": 0,
                    "products": [
                        {
                            "itemName": "Organic Spinach",
                            "itemCode": "OrgSpinach",
                            "qualityOperation": 0,
                            "stock": []
                        },
                        {
                            "itemName": "Corn on the cob",
                            "itemCode": "CornCob",
                            "qualityOperation": 1,
                            "stock": []
                        },
                        {
                            "itemName": "Grannysmith Apple Bunch",
                            "itemCode": "ApplesGran",
                            "qualityOperation": 0,
                            "stock": []
                        },
                        {
                            "itemName": "Twinkies",
                            "itemCode": "Twinkies",
                            "qualityOperation": 3,
                            "stock": []
                        },
                        {
                            "itemName": "3lb Ground Beef",
                            "itemCode": "3lbGrBeef",
                            "qualityOperation": 2,
                            "stock": []
                        },
                        {
                            "itemName": "Moonberries",
                            "itemCode": "MoonBerr",
                            "qualityOperation": 0,
                            "stock": []
                        }
                    ]
                }
            }
            """;
}
