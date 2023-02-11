FROM maven:3.8.2-jdk-11

WORKDIR /state-machine

COPY . .

RUN mvn clean install verify

CMD mvn test

CMD mvn spring-boot:run