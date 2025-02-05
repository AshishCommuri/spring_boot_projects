# Use OpenJDK 17 as base image
FROM eclipse-temurin:17-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy only the necessary files first (for efficient caching)
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Make the wrapper executable and download dependencies
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Copy the project files (after dependencies are cached)
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Copy the generated JAR file to the container
RUN mkdir -p target
COPY target/ecommerce-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port (Change if needed)
EXPOSE 8080

# Start the application
CMD ["java", "-jar", "app.jar"]
