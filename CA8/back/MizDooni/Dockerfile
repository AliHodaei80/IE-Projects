# Build stage
FROM maven:3.9-eclipse-temurin-19-alpine as builder
WORKDIR /ir/ie/mizdooni
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src/ ./src/
RUN mvn package -DskipTests

FROM openjdk:19

ADD https://repo1.maven.org/maven2/co/elastic/apm/elastic-apm-agent/1.49.0/elastic-apm-agent-1.49.0.jar elastic-apm-agent.jar

COPY --from=builder /ir/ie/mizdooni/target/*.jar /MiDooniApp.jar

ENV ELASTIC_APM_SERVICE_NAME=MizDooni
ENV ELASTIC_APM_SERVER_URLS=http://localhost:8200
ENV ELASTIC_APM_SECRET_TOKEN=
ENV ELASTIC_APM_ENVIRONMENT=production
ENV ELASTIC_APM_APPLICATION_PACKAGES=ir.ir.mizdooni
ENV ELASTIC_APM_ENABLE_EXPERIMENTAL_INSTRUMENTATIONS=true
EXPOSE 8090

CMD ["java",  \
    "-javaagent:elastic-apm-agent.jar", \
    "-Delastic.apm.service_name=$ELASTIC_APM_SERVICE_NAME", \
    "-Delastic.apm.server_urls=http://localhost:8200", \
    "-Delastic.apm.secret_token=$ELASTIC_APM_SECRET_TOKEN", \
    "-Delastic.apm.environment=$ELASTIC_APM_ENVIRONMENT", \
    "-Delastic.apm.application_packages=$ELASTIC_APM_APPLICATION_PACKAGES", \
    "-Delastic.apm.enable_experimental_instrumentations=true", \
    "-jar", \
    "/MiDooniApp.jar"]