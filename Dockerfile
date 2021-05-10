FROM adoptopenjdk/openjdk11:x86_64-debian-jdk11u-nightly-slim

ENV JAVA_OPTS "-Dspring.config.location=file:///application.properties"

ADD app.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
