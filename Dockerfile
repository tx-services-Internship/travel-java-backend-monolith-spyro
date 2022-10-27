FROM amazoncorretto:17-alpine
EXPOSE 8082
WORKDIR /app
COPY travel/target/travel.jar .
USER 1000:1000
CMD ["java","-jar", "-Djava.security.egd=file:/dev/./urandom","-Dspring.config.location=classpath:/,/app/","-Dspring.profiles.active=prod", "/app/travel.jar", "--log-opt max-size=500m"]
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75"
