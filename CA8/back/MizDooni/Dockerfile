FROM maven:3.9-eclipse-temurin-19-alpine as builder
WORKDIR /ir/ie/mizdooni
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src/ ./src/
RUN mvn package -DskipTests

FROM openjdk:19
COPY --from=builder /ir/ie/mizdooni/target/*.jar MiDooniApp.jar
ENTRYPOINT ["java", "-jar", "/MiDooniApp.jar"]