# ====== STAGE 1 : BUILD ======
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Copier les fichiers Maven
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Télécharger les dépendances (cache Docker)
RUN chmod +x mvnw && ./mvnw dependency:go-offline

# Copier le code source
COPY src src

# Build du JAR
RUN ./mvnw clean package -DskipTests


# ====== STAGE 2 : RUN ======
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
