FROM openjdk:8
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8090
ENTRYPOINT ["java", "-Djava.security.egdxfile:/dev/./urandom", "-jar", "/app.jar"]