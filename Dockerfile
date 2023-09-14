FROM openjdk:17-jdk-alpine
LABEL authors="nicholaselp"
ADD build/libs/crypto-recommendation-service-1.0.jar /build/libs/crypto-recommendation-service-1.0.jar
COPY csv-upload /csv-upload
ENTRYPOINT ["java", "-jar", "/build/libs/crypto-recommendation-service-1.0.jar"]