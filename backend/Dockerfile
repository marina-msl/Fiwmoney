FROM openjdk:17
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8090
CMD ["java", "-jar", "app.jar"]