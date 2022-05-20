FROM openjdk:17
COPY ./target/backWeb-0.0.1-SNAPSHOT.jar usr/app/

WORKDIR /usr/app

ENTRYPOINT ["java","-jar","backWeb-0.0.1-SNAPSHOT.jar"]