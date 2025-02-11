FROM amazoncorretto:21.0.5-alpine
WORKDIR /app
COPY target/PdfService.jar .
EXPOSE 8084
CMD ["java", "-jar", "PdfService.jar"]