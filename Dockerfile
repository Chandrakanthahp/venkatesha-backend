# 1. Use official OpenJDK 17 image
FROM openjdk:17-jdk-slim

# 2. Set working directory inside the container
WORKDIR /app

# 3. Copy Maven wrapper and pom.xml first (for caching dependencies)
COPY mvnw pom.xml ./
COPY .mvn .mvn

# 4. Copy the rest of the project
COPY src src

# 5. Package the application (skip tests for faster build)
RUN ./mvnw clean package -DskipTests

# 6. Run the Spring Boot jar
CMD ["java", "-jar", "target/demo-0.0.1-SNAPSHOT.jar"]
