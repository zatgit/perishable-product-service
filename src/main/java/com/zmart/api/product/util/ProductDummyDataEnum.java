package com.zmart.api.product.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductDummyDataEnum implements ProductDummyData {

    SPINACH("Organic Spinach", "OrgSpinach", 0),
    CORN("Corn on the cob", "CornCob", 1),
    APPLE("Grannysmith Apple Bunch", "ApplesGran", 0),
    TWINKIES("Twinkies", "Twinkies", 3),
    BEEF("3lb Ground Beef", "3lbGrBeef", 2),
    MOON("Moonberries", "MoonBerr", 0);

    private final String itemName;
    private final String itemCode;
    private final Integer qualityOperation;

    @Override
    public int getOrdinal() {
        return super.ordinal();
    }

    @Getter
    @AllArgsConstructor
    public enum InventoryDummyDataEnum implements InventoryDummyData {
        INV_SPINACH(SPINACH.itemName, SPINACH.itemCode, 10, 20),
        INV_CORN(CORN.itemName, CORN.itemCode, 10, 12),
        INV_APPLE(APPLE.itemName, APPLE.itemCode, 5, 7),
        INV_TWINKIES(TWINKIES.itemName, TWINKIES.itemCode, 1, 50),
        INV_BEEF(BEEF.itemName, BEEF.itemCode, 15, 10),
        INV_MOON(MOON.itemName, MOON.itemCode, 15, 20);

        private final String itemName;
        private final String itemCode;
        private final Integer sellBy;
        private final Integer quality;
    }

    @Getter
    @AllArgsConstructor
    public enum ProductDummyData50DayOffsetEnum implements ProductDummyData {
        SPINACH_OFFSET(SPINACH.itemName, SPINACH.itemCode, SPINACH.qualityOperation),
        CORN_OFFSET(CORN.itemName, CORN.itemCode, CORN.qualityOperation),
        APPLE_OFFSET(APPLE.itemName, APPLE.itemCode, APPLE.qualityOperation),
        TWINKIES_OFFSET(TWINKIES.itemName, TWINKIES.itemCode, TWINKIES.qualityOperation),
        BEEF_OFFSET(BEEF.itemName, BEEF.itemCode, BEEF.qualityOperation),
        MOON_OFFSET(MOON.itemName, MOON.itemCode, MOON.qualityOperation);

        private final String itemName;
        private final String itemCode;
        private final Integer qualityOperation;

        @Override
        public int getOrdinal() {
            return super.ordinal();
        }

        @Getter
        @AllArgsConstructor
        public enum InventoryTestData50DayOffset implements InventoryDummyData {
            INV_SPINACH(SPINACH_OFFSET.itemName, SPINACH_OFFSET.itemCode, -40, 0),
            INV_CORN(CORN_OFFSET.itemName, CORN_OFFSET.itemCode, -48, 50),
            INV_APPLE(APPLE_OFFSET.itemName, APPLE_OFFSET.itemCode, -45, 0),
            INV_TWINKIES(TWINKIES_OFFSET.itemName, TWINKIES_OFFSET.itemCode, 0, 80),
            INV_BEEF(BEEF_OFFSET.itemName, BEEF_OFFSET.itemCode, 15, 20),
            INV_MOON(MOON_OFFSET.itemName, MOON_OFFSET.itemCode, -35, 0);

            private final String itemName;
            private final String itemCode;
            private final Integer sellBy;
            private final Integer quality;
        }
    }
}
