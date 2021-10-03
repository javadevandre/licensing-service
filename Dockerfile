# Multistage build

## Stage 1
FROM openjdk:11-slim AS build

LABEL maintainer="André Luiz Cirino dos Santos <javadev.andre@gmail.com>"

ARG JAR_FILE
COPY ${JAR_FILE} app.jar

RUN mkdir -p target/dependency && cd target/dependency && jar -xf /app.jar

## Stage 2
FROM openjdk:11-slim

VOLUME /tmp

ARG DEPENDENCY=/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF  /app/META-INF

ENTRYPOINT ["java", "-cp", "app:app/lib/*", "com.optimagrowth.license.LicensingServiceApplication"]