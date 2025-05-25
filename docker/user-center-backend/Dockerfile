FROM openjdk:8-jdk-alpine

WORKDIR /app

RUN mkdir -p /app/config
RUN mkdir -p /app/logs

COPY user-center-api-0.0.1-SNAPSHOT.jar /app/
COPY application.yml /app/config
COPY logback-spring.xml /app/config

CMD ["java", "-jar", "/app/user-center-api-0.0.1-SNAPSHOT.jar"]