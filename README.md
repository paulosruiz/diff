# diff

## Project Overview
An application that provides two endpoints that receives Base64 encoded data and two endpoints, one to compare the saved data and other to retrieve all the existing data.

## Download / Clone Repository
Download the ZIP file of this repository or clone it.
If you decided to download the ZIP file, unzip it and access the project folder.

If you decided to clone it, access the project folder after performing the clone.

## Start the Application
To start the application via command line, execute the command `java -jar build/libs/diff-0.1.0.jar` at root level of the project folder.

To start the application via Spring Boot, execute the command `gradlew bootRun`.

## API Endpoints

>**POST**:  http://localhost:8080/v1/diff/{id}/right

>**POST**:  http://localhost:8080/v1/diff/{id}/left

>**GET**:   http://localhost:8080/v1/diff/{id}

>**GET**:   http://localhost:8080/v1/diff/retrieveAll

## API Samples
This section has two samples ids that can be used to perform a request to the application.

### Id 1 - Data matches

#### Set Right Data
>curl -H "Content-Type: application/json" -X POST -d "{\"data\":\"QUFBQQ==\"}" http://localhost:8080/v1/diff/1/right

#### Set Left Data
>curl -H "Content-Type: application/json" -X POST -d "{\"data\":\"QUFBQQ==\"}" http://localhost:8080/v1/diff/1/left

#### Compare
>curl -X GET -H "Content-Type: application/json"  http://localhost:8080/v1/diff/1

### Id 2 - Data does not match

#### Set Right Data
>curl -H "Content-Type: application/json" -X POST -d "{\"data\":\"QUFBQQ==\"}" http://localhost:8080/v1/diff/2/right

#### Set Left Data
>curl -H "Content-Type: application/json" -X POST -d "{\"data\":\"QkFBQg==\"}" http://localhost:8080/v1/diff/2/left

#### Compare
>curl -X GET -H "Content-Type: application/json"  http://localhost:8080/v1/diff/2

#### Retrieve All
>curl -X GET -H "Content-Type: application/json"  http://localhost:8080/v1/diff/retrieveAll

##### Note: Use Backslash before each quotes on the curl command on Windows

## API Documentation
REST API [Documentation](http://localhost:8080/swagger-ui.html)
##### To access this API Documentation the application should be running.

## Used Technologies & Frameworks
1. Java 8
2. Spring Boot
3. H2
4. Junit 
5. Swagger
6. Gradle

## Build application
If any change has been done in the application, execute the command `gradlew build` to perform the build process
##### Note: This should be performed at the project root folder.

## Test Results
The test results page is stored under `build/reports/test/index.html` from the project root folder. 

## Improvements Done
1. Included a retrieveAll endpoint that lists the existings Ids and data that were saved in the application.
2. Included a property status in the response, to allow an easier validation of the compare response.

## Next Steps
1. Add a security restriction on retrieveAll endpoint.
2. Create a endpoint to generate the Ids with left and right in a single request (v2).
3. Improve Exception handling.
