FROM amazoncorretto:21.0.5-alpine
WORKDIR /app
COPY target/ServiceDiscovery.jar .
EXPOSE 8761
CMD ["java", "-jar", "ServiceDiscovery.jar"]