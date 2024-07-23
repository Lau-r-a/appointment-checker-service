FROM maven:3.9.8-eclipse-temurin-22-jammy
WORKDIR /service
COPY pom.xml .
RUN mvn dependency:go-offline
COPY ./src ./src
RUN mvn clean install -Dmaven.test.skip=true

FROM eclipse-temurin:22-alpine
WORKDIR /service
COPY --from=0 /service/target/service-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

EXPOSE 8080