FROM openjdk:11
ARG JAR_FILE=target/CMSS_Projet-0.0.2-Production.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]