FROM openjdk:21-jdk

COPY build/libs/Transaction_service-0.0.1-SNAPSHOT.jar transactions.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "transactions.jar"]