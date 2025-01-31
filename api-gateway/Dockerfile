FROM amazoncorretto:21.0.5-alpine
WORKDIR /app
COPY target/ApiGateway.jar .
EXPOSE 8080
CMD ["java", "-jar", "ApiGateway.jar"]