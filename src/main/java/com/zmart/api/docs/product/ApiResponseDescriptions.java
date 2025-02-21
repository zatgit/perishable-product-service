package com.zmart.api.docs.product;

import lombok.experimental.UtilityClass;

@UtilityClass
@SuppressWarnings("java:S5665")
public final class ApiResponseDescriptions {

    public static final String QUALITY_OP_0_DESC =
            """
            <li><b>0</b>: <i>Quality</i> decrements daily by 1.
            Once the <i>sellByDate</i> has passed, <i>quality</i> degrades twice as fast.
           </li>
            """;

    public static final String QUALITY_OP_1_DESC =
           """
           <li><b>1</b>: <i>Quality</i> increments daily by 1 and continues incrementing by 1 after the
           <i>sellByDate</i> has passed.</li>
           """;

    public static final String QUALITY_OP_2_DESC =
           """
           <li><b>2</b>: <i>Quality</i> increments daily by 1.
           When 10 or fewer days remain until the <i>sellByDate</i>, <i>quality</i> increments daily by 2.
           After the <i>sellByDate</i> has passed, <i>quality</i> drops to 0. 
          </li>
           """;

    public static final String QUALITY_OP_3_DESC =
         """
         <li><b>3</b>: <i>Quality</i> never decrements.</li>
         """;

    public static final String SWAGGER_QUALITY_OP_RENDER = "#### Quality Operations:\n\n"
            + "<ul>"  + QUALITY_OP_0_DESC + QUALITY_OP_1_DESC + QUALITY_OP_2_DESC + QUALITY_OP_3_DESC + "</ul>\n\n"
            + "An item's <code>quality</code> may never fall below 0.\n\n"
            + "An item's <code>sellBy</code> decrements daily by 1.";

    public static final String SORT_AND_OFFSET_AND_QUERY_DESC =
            """
            &nbsp;and calculate values & dates based on days to offset (``dayOffset``) & ``qualityOperation``.
            #### Sort, Filter, Limit:
            <ul>
            <li>Where applicable, sort & filter query params apply to both parent product objects and nested stock items.</li>
            <li><i>Limit</i> applies to the total nested stock items across all product objects.</li>
            </ul>\n\n
            """;

    public static final String FUTURE_DATE_5_GIVEN_QUALITY_OP_0_DESC =
            """
            \n\nNote that the ``futureDate`` property represents the adjusted date for calculations
            and that ``quality`` & ``sellBy`` have decremented by 5.
            \n\nThe days between ``stockDate`` and ``futureDate`` are used when computing these values.
            """;

    public static final String FUTURE_DATE_5_GIVEN_QUALITY_OP_0_5_GET_ALL_DESC =
           """
           \n\nNote that the ``futureDate`` property represents the adjusted date for calculations
           and that ``quality`` & ``sellBy`` values for each inventory item have updated 
           in accordance with ``qualityOperation`` values.
           """;

    public static final String FUTURE_DATE_NOT_GIVEN_DESC =
            """
            \n\nNote that there is *no* ``futureDate`` property 
            and that ``quality`` & ``sellBy`` have *not* decremented.
            \n\nThe days between ``stockDate`` and ``currentDate`` are used when computing these values.
            """;    
    
    public static final String FUTURE_DATE_NOT_GIVEN_GET_ALL_DESC =
            """
            \n\nNote that there is *no* ``futureDate`` property 
            and that ``quality`` & ``sellBy`` values for each inventory item have *not* updated.
            \n\nThe days between ``stockDate`` and ``currentDate`` are used when computing these values.
            """;

    public static final String BAD_REQUEST_MISSING_PARAM_DESCRIPTION =
            """
            This error occurs when a required request parameter is missing from the request.
            Ensure all required parameters are included in the request.
            """;

    public static final String BAD_REQUEST_VALIDATION_DESCRIPTION =
            """
            This error occurs when one or more fields fail validation.
            Ensure all required fields are correctly formatted and provided.
            """;

    public static final String BAD_REQUEST_METHOD_NOT_ALLOWED_DESCRIPTION =
            """
            Method Not Allowed - The HTTP method used is not supported for this endpoint.
            """;

    public static final String BAD_REQUEST_METHOD_ARG_TYPE_MISMATCH_DESCRIPTION =
            """
            Method Not Allowed - The HTTP method used is not supported for this endpoint.
            """;

    public static final String BAD_REQUEST_MALFORMED_UUID_DELETE_DESCRIPTION =
            """
            Http Message Not Readable - Malformed request body with an invalid UUID format.
            """;

    public static final String BAD_REQUEST_MALFORMED_UUID_GET_BY_ID_DESCRIPTION =
            """
            Method Argument Type Mismatch - Malformed query param with an invalid UUID format.
            """;

    public static final String BAD_REQUEST_INVALID_PROPS_CREATE_DESCRIPTION =
            """
            Method Argument Not Valid - Validation failed for create request properties.
            """;

    public static final String BAD_REQUEST_NULL_QUALITY_PARENT_AND_CHILD_CREATE_DESCRIPTION =
            """
            Method Argument Not Valid - Must include quality in either JSON root or child elements.
            """;
}