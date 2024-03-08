# Use a base image with Java installed
FROM openjdk:11-jre-slim

# Set the working directory
WORKDIR /app

# Copy the compiled Java application JAR file
COPY target/your-app.jar /app/your-app.jar

# Set the command to run your application
CMD ["java", "-jar", "your-app.jar"]
