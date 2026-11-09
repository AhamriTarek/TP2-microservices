# Étape 1 : compilation avec Maven
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Le pom.xml déclare java.version=25 ; on force la compilation en 17 pour rester
# compatible avec l'image temurin-17 (pom.xml non modifiable).
RUN mvn -q -B clean package -DskipTests -Dmaven.compiler.release=17

# Étape 2 : image d'exécution légère
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","app.jar"]
