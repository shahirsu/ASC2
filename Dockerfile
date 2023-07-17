FROM openjdk:8

RUN apt-get update && \
    apt-get install build-essential maven default-jdk -y && \
    update-alternatives --config javac
COPY . .

CMD ["mvn", "spring-boot:run"]