FROM maven:3.6.3-jdk-11-slim AS builder

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY . .

RUN mvn -Dmaven.test.skip=true package

FROM openjdk:11-slim

WORKDIR /app

COPY --from=builder /app/target/lemon-0.0.1-SNAPSHOT.jar /app/app.jar

# COPY "target/lemon-0.0.1-SNAPSHOT.jar" "app.jar"
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
