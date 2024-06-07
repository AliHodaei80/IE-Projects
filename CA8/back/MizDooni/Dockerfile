# Build stage
FROM maven:3.9-eclipse-temurin-19-alpine as builder
WORKDIR /ir/ie/mizdooni
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src/ ./src/
RUN mvn package -DskipTests

FROM openjdk:19

ADD https://repo1.maven.org/maven2/co/elastic/apm/elastic-apm-agent/1.49.0/elastic-apm-agent-1.49.0.jar /elastic-apm-agent.jar

COPY --from=builder /ir/ie/mizdooni/target/*.jar /MiDooniApp.jar

ENTRYPOINT ["java", "-javaagent:/elastic-apm-agent.jar", "-Delastic.apm.service_name=MizDooni", "-Delastic.apm.server_urls=http://localhost:8200", "-Delastic.apm.secret_token=", "-Delastic.apm.environment=production", "-Delastic.apm.application_packages=ir.ir.mizdooni", "-jar", "/MiDooniApp.jar"]
