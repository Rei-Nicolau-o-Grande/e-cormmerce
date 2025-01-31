FROM amazoncorretto:21.0.5-alpine
WORKDIR /app
COPY target/Product.jar .
EXPOSE 8082
CMD ["java", "-jar", "Product.jar"]