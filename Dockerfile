FROM openjdk:8-jdk-alpine

COPY target/credit-card-api-0.0.1-SNAPSHOT.jar credit-card-api-1.0.0.jar

ENTRYPOINT ["java","-jar","/credit-card-api-1.0.0.jar"]