FROM bellsoft/liberica-openjdk-alpine:17
WORKDIR /app
# Копируем JAR нашего auth-сервиса
COPY target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]