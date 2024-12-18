FROM ubuntu:latest As build

RUN apt-get update
RUN apt-get install openjdk-23-jdk -y

COPY . .

RUN apt-get install maven -y
RUN mvn clear install