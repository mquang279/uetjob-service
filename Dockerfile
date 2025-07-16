# Build Stage
FROM maven:3.8.3-openjdk-17 as build

WORKDIR /app
COPY . .

RUN mvn install -DskipTests=true

# Run Stage
FROM openjdk:17-jdk-alpine

RUN adduser -D uetjob

WORKDIR /run
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar /run/demo-0.0.1-SNAPSHOT.jar
COPY --from=build /app/.env /run/.env

RUN chown -R uetjob:uetjob /run

USER uetjob

EXPOSE 8080

ENTRYPOINT java -jar /run/demo-0.0.1-SNAPSHOT.jar




