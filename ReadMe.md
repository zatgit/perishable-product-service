<div id="top">

# perishable-product-service

</div>

A RESTful API for tracking a grocery store's inventory of perishable goods developed with Java and Spring.


Zach Trembly

<a href="https://www.linkedin.com/in/zat/"><img alt="My LinkedIn" src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white"></a>

## Technologies
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Redis](https://img.shields.io/badge/redis-%23DD0031.svg?&style=for-the-badge&logo=redis&logoColor=white)

Java 17, Spring 6, Spring Boot 3, Maven, Docker, Redis, Swagger

## Getting Started

#### Run Application

<pre>$ mvn spring-boot:run</pre>

#### Build a Container

<pre>
$ mvn clean install && docker build -t zmart-api:latest . && docker run -p 8181:8181 zmart-food:latest .
</pre>

## API Endpoints and Documentation

Navigate to Swagger on Github Pages to view endpoints with examples.

https://zatgit.github.io/perishable-product-service

![Swagger Preview](src/main/resources/assets/images/swagger-preview.gif)

## Usage

This inventory service tracks each product's inventory data, including sell by date and quality. Most items
follow standard depreciation rules, decreasing the quality by 1 as the sell by date approaches. Some items follow their
own custom depreciation rules.

#### Exceptions and Validation

Exceptions are handled globally.
Many exceptions are thrown by Jakarta constraint violations.
Entity builders are validated using groups and custom validators.

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

Logs are handled with Slf4j, Logback, listeners, and by wrapping interceptors.

#### Caching

Distributed caching is handled with Spring Data Redis. An embedded server is included for local development. 
Key generation uses method-specific custom annotations, found in the ``cache`` package.

Two cron jobs refresh the cache daily and in 10 second intervals. These values can be adjusted dynamically if a 
configmap has been implemented. You can view these jobs under the ``scheduledtasks`` actuator endpoint.

There is a cache REST controller in the cache package for manual cache inspection, creation, and eviction.
The ``caches`` actuator endpoint has also been enabled.

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

## H2 (Database)

See [application-h2.properties](src/main/resources/application-h2.properties) 
for console, username & password.

<hr></hr>

<br><a href="#top">Go to top</a></p>

