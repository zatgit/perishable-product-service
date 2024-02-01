package com.zmart.api.product.controller.swagger;

import lombok.experimental.UtilityClass;

@UtilityClass
@SuppressWarnings("java:S5665")
public final class SwaggerProdDescriptionConstants {

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
            #### DataView:
            <ul>
            <li>Where applicable, sort args apply to both parent product objects and nested stock items.</li>
            <li><i>Limit</i> applies to the total nested stock items across all product objects.</li>
            <li>If <i>dataView</i> or any of its properties are omitted, defaults are generated programmatically.</li>
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
}