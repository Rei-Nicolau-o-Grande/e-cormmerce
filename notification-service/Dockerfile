FROM amazoncorretto:21.0.5-alpine
WORKDIR /app
COPY target/Notification.jar .
EXPOSE 8083
CMD ["java", "-jar", "Notification.jar"]