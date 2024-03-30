FROM maven:3.8.3-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ /app/src
RUN mvn clean install -DskipTests


FROM eclipse-temurin:17-jdk-alpine
EXPOSE 8080
COPY --from=build /app/target/tech-challenge-pos-tech-gateway-1.0.0.jar tech-challenge-pos-tech-gateway.jar

ARG URL_CLIENT
ARG URL_COGNITO

ENV URL_CLIENT=$URL_CLIENT
ENV URL_COGNITO=$URL_COGNITO

ENV JAVA_APP_ARGS="--spring.config.location=/src/main/resources/application.properties"

ENTRYPOINT ["java","-jar","tech-challenge-pos-tech-gateway.jar", "$JAVA_APP_ARGS"]