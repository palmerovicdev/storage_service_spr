# Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim

# Add Maintainer Info
LABEL maintainer="palmerodev@gmail.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE

# Environment Variables ARGS
ARG APP_STORAGE_SERVICE_URL
ARG DB_HOST
ARG DB_PORT
ARG DB_NAME
ARG DB_USER
ARG DB_PASSWORD

# Environment Variables
ENV APP_STORAGE_SERVICE_URL=$APP_STORAGE_SERVICE_URL
ENV DB_HOST=$DB_HOST
ENV DB_PORT=$DB_PORT
ENV DB_NAME=$DB_NAME
ENV DB_USER=$DB_USER
ENV DB_PASSWORD=$DB_PASSWORD

# Add the application's jar to the container
ADD ${JAR_FILE} app.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]