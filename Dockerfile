FROM openjdk:11
COPY ./target/tokioFilme-0.0.1-SNAPSHOT.jar .
RUN touch film-fanatic-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "-Dserver.port=8080", "-XX:+UseContainerSupport", "-Dfile.encoding=UTF-8", "film-fanatic-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=prod"]
