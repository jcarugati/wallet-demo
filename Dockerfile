FROM maven:3.6.3-jdk-11-slim

COPY "target/demo-0.0.1-SNAPSHOT.jar" "app.jar"
ENTRYPOINT ["java", "-jar", "app.jar"]
