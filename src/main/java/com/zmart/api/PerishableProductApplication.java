package com.zmart.api;

import com.zmart.api.product.repository.BaseRepositoryImpl;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import static com.zmart.api.docs.ApiDocsConstants.API_DESCRIPTION;
import static com.zmart.api.docs.ApiDocsConstants.GPL3_URL;

@OpenAPIDefinition(info=@Info(
        title="Perishable Product Service",
        description = API_DESCRIPTION,
        license = @License(name = "GPL 3", identifier = "GPL-3.0", url = GPL3_URL)),
        servers = {
                @Server(url = "${server.url.production}", description = "Production"),
                @Server(url = "${server.url.local}", description = "Local")
        })
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
