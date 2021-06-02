FROM openjdk:8-jdk-alpine
LABEL org.opencontainers.image.source="https://github.com/valentine-danilov/guild-api"
ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,address=8000,server=y,suspend=n
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
