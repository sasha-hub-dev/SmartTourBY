FROM bellsoft/liberica-openjdk-alpine:25
WORKDIR /app
# Копируем ИМЕННО tour-service
COPY target/tour-service-*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]