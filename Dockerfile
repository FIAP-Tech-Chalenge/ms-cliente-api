# Primeira etapa: Construir a aplicação
FROM maven:3.9.5-amazoncorretto-21 AS build

WORKDIR /workspace

# Copie o pom.xml e baixe as dependências, isso melhora o cache do Docker
COPY pom.xml .
RUN mvn dependency:go-offline

# Copie o código fonte e construa o JAR sem executar os testes
COPY src src
RUN mvn clean package -DskipTests

# Segunda etapa: Rodar a aplicação
FROM amazoncorretto:21-alpine-jdk

LABEL maintainer="ricardo@ricardolm.net"
LABEL version="1.0"
LABEL description="FIAP - Tech Chalenger"
LABEL name="MS Cliente API"

EXPOSE 8080

# Copie o JAR da primeira etapa
COPY --from=build /workspace/target/ms-cliente-api-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]