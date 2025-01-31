FROM amazoncorretto:21.0.5-alpine
WORKDIR /app
COPY target/Order.jar .
EXPOSE 8081
CMD ["java", "-jar", "Order.jar"]