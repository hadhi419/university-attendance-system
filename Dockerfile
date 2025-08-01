FROM openjdk:17-jdk-slim
WORKDIR /app
COPY Backend/build/university-attendance-system-backend-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]
