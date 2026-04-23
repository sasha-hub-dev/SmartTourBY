FROM bellsoft/liberica-openjdk-alpine:17
WORKDIR /app
# Копируем JAR, собранный Maven
COPY target/booking-service-*.jar app.jar
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "app.jar"]