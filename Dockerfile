FROM maven:3.8.3-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ /app/src
RUN mvn clean install -DskipTests


FROM eclipse-temurin:17-jdk-alpine
EXPOSE 8080
COPY --from=build /app/target/api-gateway-1.0.0.jar api-gateway.jar

ARG URL_CLIENT_SERVER

ENV URL_CLIENT_SERVER=$URL_CLIENT_SERVER
ENV JAVA_APP_ARGS="--spring.config.location=/src/main/resources/application.yaml"

ENTRYPOINT ["java","-jar","api-gateway.jar", "$JAVA_APP_ARGS"]