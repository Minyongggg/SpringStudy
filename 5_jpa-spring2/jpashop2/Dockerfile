FROM openjdk:11
LABEL authors="chimac"

WORKDIR /app

ARG JAR_PATH=./build/libs

COPY ${JAR_PATH}/jpashop-0.0.1-SNAPSHOT.jar ${JAR_PATH}/jpashop-0.0.1-SNAPSHOT.jar

CMD ["java", "-jar", "./build/libs/jpashop-0.0.1-SNAPSHOT.jar"]