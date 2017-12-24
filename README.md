# diff



#### Download / Clone Repository
Download the ZIP file of this repository or clone it.
If you decided to download the ZIP file, unzip it and access the project folder.
If you decided to clone it, access the project folder after performing the clone.

#### Start the Application
To start the application via java command line, execute the following command at the root level of the project folder `java -jar build/libs/diff-0.1.0.jar`.
To start the application via Spring Boot, execute the command `gradlew bootRun`.

#### API
1. Set Data at Right Position
>**POST**:  [http://localhost:8080/v1/diff/{ID}/right] (http://localhost:8080/v1/diff/{ID}/right)
2. Set Data at Left Position
>**POST**:  [http://localhost:8080/v1/diff/{ID}/left] (http://localhost:8080/v1/diff/{ID}/left)
3. Compare Data
>**GET**:   http://localhost:8080/v1/diff/{ID}
4. Retrieve All
>**GET**:   http://localhost:8080/v1/diff/retrieveAll

#### API Documentation
REST API [Documentation] (http://localhost:8080/swagger-ui.html)
###### To access this API Documentation the application should be running.

#### Used Technologies
1. Java 8
2. Spring Boot
3. H2 Database 
4. Swagger

#### Build the Application
If any change has been done in the application, execute the command `gradlew build` to perform the build process
###### Note: This should be performed at the project root folder.







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