# Employee REST Server

This project exposes REST operations for employees, allowing to create, read, update and delete employees from an in-memory database.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine.

### Prerequisites

* Java 1.8.x
* Maven 3.x.x

### Installing and Running

-   Clone this repo to your local machine using 
````
https://github.com/edruval/employee-rest-server
````
-   Clean and install
````
cd employee-rest-server
mvn clean
mvn install (If you want to skip the tests add -DskipTests=true)
````
- Run the app
````
java -jar target\employee-management-0.0.1-SNAPSHOT.jar
````

On startup, the application reads an external source of employees to populate the database. You can edit the file in order to change the initial data.
> initial-employees.csv

### How to Use
The server runs locally at http://localhost:8080/api/v1/employees
You can use postman or any other application that allows you to make HTTP requests.

The following HTTP operations are available:

 - **GET**    /api/v1/employees *`Gets all 'active' employees`* 
    >  Returns HTTP 200 and the list of employees.
   
   Request example:
   ````
    http://localhost:8080/api/v1/employees
   ````
   
- **GET**    /api/v1/employees/{id} *`Gets an 'active' employee by id`*
    > Returns HTTP 200 and the employee details.
    > Returns HTTP 404 if the employee was not found.

	Request example:
   ````
    http://localhost:8080/api/v1/employees/1
   ````
    
- **POST**   /api/v1/employees *`Creates a new 'active' employee`*
    > Returns HTTP 200 and the details of the created employee.
    > Returns HTTP 400 if the request is invalid.
   
    Request example:
   ````
    http://localhost:8080/api/v1/employees
    {
	  "firstName": "Edgar",
	  "middleInitial": "F",
	  "lastName": "Ruvalcaba",
      "dateOfBirth": "1987-01-30",
      "dateOfEmployment": "2011-01-01"
	}
   ````
- **PUT**  /api/v1/employees/{id} *`Updates an existing employee`*
    > Returns HTTP 200 and the id of the updated employee.
    > Returns HTTP 400 if the request is invalid.
    
    Request example:
   ````
    http://localhost:8080/api/v1/employees/1
    {
	  "firstName": "John",
	  "middleInitial": "X",
	  "lastName": "Doe",
      "dateOfBirth": "1999-09-09",
      "dateOfEmployment": "2019-09-09"
	}
   ````
- **DELETE** /api/v1/employees/{id}*`Changes employee status from 'active' to 'inactive'`* 
*This operation requires 'Authorization' header (Basic YWRtaW46U2VjcjN0IQ==)*
    > Returns HTTP 200 if the employee was inactivated successfully.
    > Returns HTTP 401 if the user is not authorized.
    > Returns HTTP 404 if the employee was not found.

    Request example:
   ````
    http://localhost:8080/api/v1/employees/1
    Authorization: Basic YWRtaW46U2VjcjN0IQ==
   ````


## Built With

-   [Spring Boot](https://spring.io/projects/spring-boot/)  - The web framework used
-   [Spring Security](https://spring.io/projects/spring-security/)  - Authentication and access-control
-   [Maven](https://maven.apache.org/)  - Dependency management
-   [H2 Database](https://www.h2database.com/)  - In-memory database
-   [JUnit](https://junit.org/) - Integration and unit testing
-   [Mockito](https://site.mockito.org/) - Mock objects
-   [Lombok](https://projectlombok.org/)  - Reduce repetitive code

### Design Patterns
- **Builder** - Makes easier the operations of building objects encapsulating the creation logic. It also helps to reuse code when creating objects from the Model or Data Transfer Objects.

- **DAO** - Separates the data access interface from its data access mechanism, making easier to replace the source of data when needed.

- **Dependency Injection** - Helps separating classes from its dependencies, so the code is more reusable and allows replacing dependencies without impacting the class that uses them.

- **Template Method** - Lets subclasses redefine certain steps of an algorithm. It helps reduce and reuse code for integration tests by using a template for the REST calls.



## Author

-   **Edgar Ruvalcaba**
