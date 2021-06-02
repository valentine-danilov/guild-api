FROM openjdk:8-jdk-alpine
LABEL org.opencontainers.image.source="https://github.com/valentine-danilov/guild-api"
ARG JAR_FILE=build/libs/*.jar
ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,address=8000,server=y,suspend=n
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
