FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY Backend/ /app/
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/university-attendance-system-backend-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
