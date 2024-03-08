# Use a base image with JDK
FROM adoptopenjdk/openjdk11:alpine-jre

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled Spring Boot application JAR file into the container
COPY ./ /app/expense.jar

# Expose the port your application runs on
EXPOSE 8086

# Command to run the Spring Boot application
CMD ["java", "-jar", "expense.jar"]
