# Utiliser une image de base avec Java et Maven préinstallés
FROM maven:3.6.3-openjdk-17-hadil AS build

# Copier le code source dans l'image
WORKDIR /app
COPY . /app

# Construire l'application avec Maven
RUN mvn clean package

RUN apk add openjdk17

# Exécuter l'application Spring Boot
CMD ["java", "-jar", "target/achat-1.0.jar"]
