package com.zmart.api.product.data;

import com.zmart.api.product.repository.ProductRepository;
import com.zmart.api.product.util.ProductDummyDataEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import static com.zmart.api.product.util.ProductUtility.buildProductDummyData;

@ConditionalOnProperty(
        value = "local.database.prefill-data",
        havingValue = "on",
        matchIfMissing = true)
@Component
@SuppressWarnings({"java:S3010", "java:S2209"})
public class ProductDataLoader implements CommandLineRunner {

    private static ProductRepository productRepository;

    @Autowired
    public ProductDataLoader(
            final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(final String... args) {
        loadProductData();
    }

    private static void loadProductData() {
        productRepository.saveAll(
                buildProductDummyData(
                        ProductDummyDataEnum.values(),
                        ProductDummyDataEnum.InventoryDummyDataEnum.values()));
    }
}
