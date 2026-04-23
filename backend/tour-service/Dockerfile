FROM bellsoft/liberica-openjdk-alpine:17
WORKDIR /app
# Копируем JAR, собранный Maven
COPY target/tour-service-*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]