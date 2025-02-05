# Use an OpenJDK 8 base image
FROM eclipse-temurin:8-jdk

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

# Expose the application port (Change if your app runs on a different port)
EXPOSE 8080

# Start the application
CMD ["java", "-jar", "target/*.jar"]
