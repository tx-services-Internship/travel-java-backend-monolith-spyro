# Monolithic backend for TX Internship Travel application.

Application will be used by employees to request and office managers (OM) to process daily allowance and travel expenses.

## First time setup

1. Install [Software Development Kit Manager](https://sdkman.io/)
2. Install Java and Maven
   ```shell
   sdk install 11.0.12-sem
   sdk install maven 3.8.5
   ```
3. Install [Docker service](https://docs.docker.com/get-docker/)
4. Compile project (~1min) - run the following command from project root:
   ```shell
    mvn spotless:apply clean verify 
   ```
5. Set up your code formatter by following this [guide](https://github.com/google/google-java-format#intellij-android-studio-and-other-jetbrains-ides) 

## Run local Docker setup with necessary services

This command (from project root) starts a default stack (including MariaDB):

```shell
./_environment/docker/docker-compose.yaml up
```

## JWT tokens for executing HTTP requests

In ./_rest-client/ folder are defined `http` files used to execute http calls to APIs exposed by services. JWTs for 
these
requests are generated in the [Sign in request](_rest-client/travel.http) API call which sets the cookie with JWT 
for all other requests to use. To test the application, make sure to run the `travel.http` collection with local 
environment

## API docs

In order to access API docs for the travel application on your local machine
visit [link](http://localhost:8082/v3/api-docs/). To retrieve API specification(contract) open [Swagger](http://localhost:8082/swagger-ui/index.html).
