# CryptoCurrency Recommendation Service

## Description
The Cryptocurrency recommendation service reads excel files located in the [csv-upload](csv-upload) folder during application startup.

There are three endpoints defined in the [openAPI yaml](src/main/resources/openapi/crypto-recommendation-api.yaml) 
1. Returns in descending order the list of all cryptocurrencies in the application's memory comparing their normalized range (max-min) / min
2. Returns oldest/newest/min/max prices for a requested cryptocurrency
3. Returns the cryptocurrency with the highest normalized range for a specific day

## Project specifications
The recommendation service uses Gradle as its build automation tool and OpenAPI for generating DTOs and defining APIs

## Documentation
Documentation of the project can be found in the [Documentation](Documentation/index.html) folder located in the root directory

## Building the project
./gradlew clean build

## Running the spring boot application locally
In a terminal run ./gradlew bootRun

### Run APIs locally with port 8080
http://localhost:8080/crypto-recommendation/v1/XRP/info <br>
http://localhost:8080/crypto-recommendation/v1/all-sorted <br>
http://localhost:8080/crypto-recommendation/v1/highest-range?date=2022-01-07 <br>


## Dockerized spring boot app
This spring boot app contains a [DockerFile](Dockerfile) to containerize the application

### How to run
Make sure docker application is up and running.<br>
In the root directory of this project run below commands

#### Create docker image
docker build -t {imageName} . <br>
docker build -t crypto-recommendation .

#### Create and start container
docker run -p 8000:8080 {imageName} <br>
docker run -p 8000:8080 crypto-recommendation

#### Other commands
docker container ls -> see containers running<br>
docker images -> see images

### APIs can now be runned on port 8000
http://localhost:8000/crypto-recommendation/v1/XRP/info <br>
http://localhost:8000/crypto-recommendation/v1/all-sorted <br>
http://localhost:8000/crypto-recommendation/v1/highest-range?date=2022-01-07 <br>