FROM bellsoft/liberica-openjdk-alpine:25
WORKDIR /app
# Копируем ИМЕННО booking-service
COPY target/booking-service-*.jar app.jar
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "app.jar"]