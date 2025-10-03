FROM openjdk:17-jdk-slim

WORKDIR /app

COPY TCC_Backend/BarbersClubs/pom.xml .
COPY TCC_Backend/BarbersClubs/mvnw .
COPY TCC_Backend/BarbersClubs/.mvn .mvn

RUN chmod +x ./mvnw
RUN ./mvnw dependency:go-offline -B

COPY TCC_Backend/BarbersClubs/src ./src

RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/BarbesClub-0.0.1-SNAPSHOT.jar"]