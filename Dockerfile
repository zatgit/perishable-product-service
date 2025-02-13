# Multi-layer for size reduction
FROM maven:3.9.5-eclipse-temurin-17 AS builder
WORKDIR /app

COPY pom.xml ./
COPY src ./src

RUN mvn clean package -DskipTests

FROM amazoncorretto:17

# Run as non-root user
RUN yum install -y shadow-utils && useradd -m -s /bin/bash user

USER user
WORKDIR /home/user

COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8181
ENTRYPOINT ["java", "-jar", "/home/user/app.jar"]
