FROM gradle:jdk21-alpine AS BUILD
WORKDIR /app
COPY . .
RUN gradle build --no-daemon -x test

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=BUILD /app/build/libs/*.jar /app/notificacao.jar
EXPOSE 8082
CMD ["java", "-jar", "/app/notificacao.jar"]