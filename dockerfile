LABEL org.opencontainers.image.source="https://github.com/valikdanilov/guild-api"
FROM openjdk:8-jdk-alpine
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENV JPDA_ADDRESS="8000"
ENV JPDA_TRANSPORT="dt_socket"
ENTRYPOINT ["java","-jar","/app.jar"]
