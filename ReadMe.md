<div id="top">

# perishable-product-service

</div>

A RESTful API for tracking an e-grocery service's inventory of perishable goods developed with Java and Spring.

### Author
Zach Trembly

<a href="https://www.linkedin.com/in/zat/"><img alt="My LinkedIn" src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white"></a>

## Technologies
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Redis](https://img.shields.io/badge/redis-%23DD0031.svg?&style=for-the-badge&logo=redis&logoColor=white)

Java 17, Spring Boot 3, Spring Data JPA, Spring Data Redis, Spring Cache, Hibernate, Maven, Docker, Swagger

## Getting Started

#### Run the Latest API Image from AWS ECR

<pre>
$ docker run --pull=always -p 8181:8181 public.ecr.aws/l5s6j4h1/zmart-api:latest
</pre>

## API Endpoints and Documentation

This API is deployed on AWS ECS (Fargate) behind a Load Balancer for scalability and high availability. 

View the API documentation with examples and interact with the service at:

🌐 https://api.zachtrembly.com/swagger-ui/index.html

![Swagger Preview](src/main/resources/assets/images/swagger-preview.gif)

## Usage

This inventory service tracks each product's inventory data, including sell by date and quality. Most items
follow standard depreciation rules, decreasing the quality by 1 as the sell by date approaches. Some items follow their
own custom depreciation rules.

#### Exceptions and Validation

Exceptions are handled globally using a centralized exception handling mechanism.
Jakarta Validation is used across all layers, including request validation in controllers, 
business logic validation in services, and data validation in repositories.
Entity builders are validated using groups, factories, and custom validators.

<details style="font:20px Arial;"><summary>Sample Exception Response</summary>

```json
{
    "timestamp": "2025-02-12T21:16:27.474Z",
    "code": "400 BAD_REQUEST",
    "exception": "MethodArgumentNotValidException",
    "message": "Invalid properties: quality must be less than or equal to 50, provided 80",
    "cause": {
        "message": "Validation failed for argument [0] in public org.springframework.http.ResponseEntity<com.zmart.api.product.dto.response.ProductsByQualityResponse> com.zmart.api.product.controller.ProductRestController.getProductsByQuality(com.zmart.api.product.dto.request.ProductsByQualityRequest): [Field error in object 'productsByQualityRequest' on field 'quality': rejected value [80]; codes [Max.productsByQualityRequest.quality,Max.quality,Max.java.lang.String,Max]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [productsByQualityRequest.quality,quality]; arguments []; default message [quality],50]; default message [must be less than or equal to 50, provided 80]] "
    }
}
```
</details>

#### Logging

Logging is implemented using SLF4J and Logback, with listeners and interceptor wrapping for enhanced logging capabilities.

#### Caching

Distributed caching is implemented using Spring Data Redis, with an embedded Redis server included for local development.
Key generation is managed through method-specific custom annotations, located in the ``cache`` package.

Two cron jobs refresh the cache daily and every 10 seconds. 
These intervals can be adjusted dynamically using environment variables or configuration management.  
You can view these jobs under the ``scheduledtasks`` actuator endpoint.

A cache REST controller is available in the ``cache`` package for manual cache inspection, creation, and eviction.  
Additionally, the ``caches`` actuator endpoint is enabled for monitoring cache statistics.


<details style="font:20px Arial;"><summary>Sample Key</summary></summary>

<pre>"products::ProductInventoryServiceImpl,getProductsByQuality,20,100,itemName,DESC"</pre></p>
</details>

<details style="font:20px Arial;"><summary>Embedded Redis</summary>
<br> Toggle <code>local.redis.server.embedded=ON</code> in the <code>local</code> properties file 
to use the server.</p>

[Go to application-local.properties](src/main/resources/application-local.properties)
</details>

<details style="font:20px Arial;"><summary>Standalone Redis</summary>
<br>If you'd prefer a non-embedded local redis server, use the <code>prod</code> profile. 
<br>Grab the redis docker image and run the server:</p>
<br><pre>$ docker pull redis && docker run --name zmart-redis -p 6379:6379 -d redis</pre></p>

</details>

## Persistence

The project uses Spring Data JPA with Hibernate as the default ORM (Object-Relational Mapping) framework to manage persistence.
Hibernate efficiently maps Java objects to relational database tables, handling SQL generation and execution.
The database is H2 for local development.

See [application-h2.properties](src/main/resources/application-h2.properties) 
for connection details (console, username & password).

<hr></hr>

<br><a href="#top">Go to top</a></p>

