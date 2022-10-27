FROM amazoncorretto:11-alpine
WORKDIR /app
COPY travel/target/travel.jar .
USER 1000:1000
CMD ["java","-jar", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.config.location=classpath:/,/app/", "/app/travel.jar"]
