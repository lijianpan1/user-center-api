FROM openjdk:8-jdk-alpine

WORKDIR /app

RUN mkdir config
RUN mkdir logs

COPY user-center-api-0.0.1-SNAPSHOT.jar user-center.jar
COPY application.yml config
COPY logback-spring.xml config

CMD ["java", "-jar", "user-center.jar"]