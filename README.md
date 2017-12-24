# diff

###### Used Technologies
1. Java 8
2. Spring Boot
3. H2 Database 
4. Swagger

#### Start the Application
Download the zip or clone the repository

Run the command: java -jar build/libs/diff-0.1.0.jar at the project or zip root folder

Access the Rest API via to look for the methods and how to use it.
###### Note: The mongobd 3.6 should be running at localhost:27017

#### API
1. Define Data
>**POST**:  http://localhost:8080/v1/diff/{ID}/right

>**POST**:  http://localhost:8080/v1/diff/{ID}/left

2. Compare
>**GET**:   http://localhost:8080/v1/diff/{ID}

3. Retrieve All
>**GET**:   http://localhost:8080/v1/diff/retrieveAll

#### API Documentation
REST API [Documentation] (http://localhost:8080/swagger-ui.html)
###### To access the documentation the application should be running.

#### Build the Application
Perform the command `gradlew build` to start the build process
###### This should be performed at the project root folder.







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