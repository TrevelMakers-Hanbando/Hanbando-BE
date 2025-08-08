FROM eclipse-temurin:21-jre-alpine
LABEL maintainer="kimdoyun0806 <gim_doyun@naver.com>"
LABEL version="1.0"

COPY build/libs/hanbando-be-0.0.1-SNAPSHOT.jar ./app.jar
WORKDIR /app
EXPOSE 8000
CMD ["java", "-jar", "app.jar"]
#
#FROM eclipse-temurin:21-jre-alpine
#ARG JAR_FILE=build/libs/*.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.profiles.active=prod"]
