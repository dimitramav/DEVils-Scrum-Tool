# Spring boot image with java 11
FROM openjdk:11-jdk
EXPOSE 8765
ARG JAR_FILE=build/libs/*.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
