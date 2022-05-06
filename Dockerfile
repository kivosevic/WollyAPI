FROM maven:3.8.5-jdk-11 AS build
EXPOSE 8080
WORKDIR .
COPY pom.xml .
COPY src ./src
RUN mvn clean package -Dmaven.test.skip
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=default", "target/wolly-1.0.0.jar"]
