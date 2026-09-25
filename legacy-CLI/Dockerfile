FROM openjdk:19

WORKDIR /app

COPY ./src /app/

RUN javac Main.java

ENTRYPOINT ["java", "Main"]