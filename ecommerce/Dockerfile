# Use OpenJDK 17 as base image
FROM eclipse-temurin:17-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven wrapper and dependencies
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Copy the project files
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Expose the application port (Change if needed)
EXPOSE 8080

# Start the application
CMD ["java", "-jar", "target/*.jar"]
