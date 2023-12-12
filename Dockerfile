FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN apk add maven --no-cache && mvn package -DskipTests
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "target/KarGoBangWebServer-0.0.1-SNAPSHOT.jar"]
