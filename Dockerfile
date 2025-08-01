# Use official Java image
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /app

# Copy the backend folder (including pom.xml, src, etc.)
COPY Backend /app

# Build the project (skip tests for speed)
RUN mvn clean package -DskipTests

# ---------------------
# Run stage
# ---------------------
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy the JAR from the previous build stage
COPY --from=build /app/target/university-attendance-system-backend-0.0.1-SNAPSHOT.jar .

# Start the app
CMD ["java", "-jar", "university-attendance-system-backend-0.0.1-SNAPSHOT.jar"]
