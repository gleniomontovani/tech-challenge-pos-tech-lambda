FROM maven:3.8.3-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ /app/src
RUN mvn clean install -DskipTests


FROM adoptopenjdk:17-jre-hotspot
EXPOSE 8080
COPY --from=build /app/target/tech-challenge-pos-tech-lambda-1.0.0.jar tech-challenge-pos-tech-lambda.jar

ARG URL_CLIENT_SERVER

ENV URL_CLIENT_SERVER=$URL_CLIENT_SERVER
ENV JAVA_APP_ARGS="--spring.config.location=/src/main/resources/application.yaml"

ENTRYPOINT ["java","-jar","tech-challenge-pos-tech-lambda.jar", "$JAVA_APP_ARGS"]