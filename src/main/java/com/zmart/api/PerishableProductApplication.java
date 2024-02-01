package com.zmart.api;

import com.zmart.api.product.repository.BaseRepositoryImpl;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import static com.zmart.api.product.util.ProductConstants.API_DESCRIPTION;
import static com.zmart.api.product.util.ProductConstants.API_VERSION;
import static com.zmart.api.product.util.ProductConstants.GPL3_URL;

@OpenAPIDefinition(info=@Info(
        title="Perishable Product Service",
        version = API_VERSION, description = API_DESCRIPTION,
        license = @License(name = "GPL 3", identifier = "GPL-3.0", url = GPL3_URL)))
@EnableCaching
@EnableScheduling
@ConfigurationPropertiesScan
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl.class)
@SpringBootApplication(scanBasePackages = {"com.zmart.api", "org.hibernate.validator"})
public class PerishableProductApplication {
    public static void main(final String[] args) {
        SpringApplication.run(PerishableProductApplication.class, args);
    }
}
