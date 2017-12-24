# diff

#### Project Overview
An application that provides two endpoints that receives Base64 encoded data and two endpoints, one to compare the saved data and other to retrieve all the existing data.

#### Download / Clone Repository
Download the ZIP file of this repository or clone it.
If you decided to download the ZIP file, unzip it and access the project folder.
If you decided to clone it, access the project folder after performing the clone.

#### Start the Application
To start the application via command line, execute the command `java -jar build/libs/diff-0.1.0.jar` at root level of the project folder .
To start the application via Spring Boot, execute the command `gradlew bootRun`.

#### API Endpoints
>**POST**:  http://localhost:8080/v1/diff/{ID}/right
>**POST**:  http://localhost:8080/v1/diff/{ID}/left
>**GET**:   http://localhost:8080/v1/diff/{ID}]
>**GET**:   http://localhost:8080/v1/diff/retrieveAll

#### API Samples
In the root folder there are two files (sample_left.json and sample_right.json) that can be used to perform a request to the application

#### API Documentation
REST API [Documentation] (http://localhost:8080/swagger-ui.html)
###### To access this API Documentation the application should be running.

#### Used Technologies & Frameworks
1. Java 8
2. Spring Boot
3. H2 Database
4. Junit 
5. 
4. Swagger

#### Build the Application
If any change has been done in the application, execute the command `gradlew build` to perform the build process
###### Note: This should be performed at the project root folder.

###### Improvements
1. Included a retrieveAll endpoint that lists the existings Ids and datas that were saved in the application.
2. Included a property status in the response, to allow an easier validation of the compare response.

###### Next Steps
1. Add a security restriction on retrieveAll endpoint

Revisar strings das classes de teste

TODO SWAGGER
	incluir documentação no readMe
	
Declarar no Readme:
Included a property status in the response
Included a delete service

Feitos:
TODO TESTS
	Documentação
	Service Testado
	Controller Testado
	Repository Testado

TODO remover um Left & Right e deixar um só via parametro
TODO persistencia do banco
TODO Compare
TODO Controller LOGS