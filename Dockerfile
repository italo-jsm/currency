FROM gradle:jdk23-alpine AS BUILD
WORKDIR /usr/app/
COPY . .
RUN gradle clean build -x test --rerun-tasks --no-build-cache
FROM openjdk:23-jdk
ENV JAR_NAME=currency-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/usr/app
WORKDIR $APP_HOME
COPY --from=BUILD $APP_HOME .
EXPOSE 8080
ENTRYPOINT java -jar $APP_HOME/build/libs/$JAR_NAME