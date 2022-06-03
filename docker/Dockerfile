FROM openjdk:16-jdk-alpine
EXPOSE 8080
ADD ../build/libs/currency-info-0.0.1-SNAPSHOT.jar currency-info-service
ENTRYPOINT ["java","-jar","/currency-info-service"]