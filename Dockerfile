FROM openjdk:8
ADD target/SmartCity-0.0.1-SNAPSHOT.jar SmartCity-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","SmartCity-0.0.1-SNAPSHOT.jar"]