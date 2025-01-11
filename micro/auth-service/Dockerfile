# Stage 1: Build Stage
FROM gradle:7.6-jdk17 AS build

# Set the working directory
WORKDIR /app

# Copy Gradle wrapper and project files
COPY build.gradle settings.gradle /app/
COPY src /app/src

# Build the project (skipping tests)
RUN gradle clean build --no-daemon --build-cache -x test

# Stage 2: Runtime Stage
FROM openjdk:17-jdk-slim

# Set a volume to store temporary files
VOLUME /tmp

# Pass environment variables for database connection
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5433/mono-db
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=password

# Copy the built JAR from the build stage
ARG JAR_FILE=build/libs/auth-service-0.0.1-SNAPSHOT.jar
COPY --from=build /app/${JAR_FILE} app.jar

# Define the service name
LABEL service.name="auth-service"

# Expose the application's default port
EXPOSE 8090

# Run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]
