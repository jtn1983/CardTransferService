FROM adoptopenjdk/openjdk11
EXPOSE 5500
COPY target/CardService-0.0.1-SNAPSHOT.jar CardService.jar
CMD ["java", "-jar", "/CardService.jar"]