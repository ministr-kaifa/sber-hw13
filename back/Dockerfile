FROM maven:3.8.7-openjdk-18-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:22-oracle
COPY --from=build /home/app/target/hw13-1.jar /usr/local/lib/hw13.jar
EXPOSE 8080
CMD ["java","-jar","/usr/local/lib/hw13.jar"]