FROM openjdk:21-jdk-slim
EXPOSE 8080
WORKDIR /app
COPY target/rfid-tag-0.0.1-SNAPSHOT.jar /app/rfid-tag-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "rfid-tag-0.0.1-SNAPSHOT.jar"]