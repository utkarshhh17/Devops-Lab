# Use an official Maven image to build the project
FROM maven:3.8.4-openjdk-17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and the source code into the container
COPY pom.xml .
COPY src ./src

# Build the project using Maven
RUN mvn clean install

# Use a separate stage to create a smaller runtime image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/Devops-lab-0.0.1-SNAPSHOT.jar /app/Devops-lab-0.0.1-SNAPSHOT.jar

# Define the entrypoint to run the JAR file
ENTRYPOINT ["java", "-jar", "Devops-lab-0.0.1-SNAPSHOT.jar"]