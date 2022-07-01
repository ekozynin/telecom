# Background
We are a Telecom operator. In our database, we are starting to store phone numbers
associated to customers (1 customer: N phone numbers) and we will provide interfaces to
manage them.

We need to provide the below capabilities:
* get all phone numbers
* get all phone numbers of a single customer
* activate a phone number


# Requirements
1. Provide API specifications for the above functions/capabilities.
2. Provide a REST API implementation of the formulated specifications.
3. You can assume the phone numbers as a static data structure that is initialised when
   your program runs.


## Assumptions

### Get all phone numbers
We will have way too many phone numbers in the database. 
It will not be practical/possible to return them all in a single response.
To mitigate the problem, pagination has been introduced for the `get all phone numbers` endpoint.


# Application
To build application (this will also run all test cases) `./gradlew clean build` (on Windows `gradle clean build`)

To run tests `./gradlew clean check` (on Windows `gradle clean check`)

To run application `./gradlew bootRun` (on Windows `gradle bootRun`)

## Reports
Test results `<repo root>/app/build/reports/tests/test/index.html`

Spock reports `<repo root>/app/build/spock-reports/index.html`

Jacoco code coverage report `<repo root>/app/build/reports/jacoco/test/html/index.html`

Checkstyle report `<repo root>/app/build/reports/checkstyle/main.html`

PMD report `<repo root>/app/build/reports/pmd/main.html`

## API Specification
Open API documentation is generated for the application's API at runtime.

[Telecom API Specification](http://localhost:10000/telecom/v3/api-docs)

[Swagger UI](http://localhost:10000/telecom/swagger-ui/index.html) is available to browse API and to run sample requests


# Technical Notes
Project is built using the following technologies:
* Java 11
* Spring Boot Run
* Spring JPA (+ FlyWay)
* H2 (not to be used for production)
* Gradle (for build script)
* Groovy / Spock (for testing)

Quality control:
* Jacoco code coverage
* PMD
* Checkstyle

## Database
Project code is using in-memory H2 database, that is launched on the application startup and is populated with sample data. 
Flyway is used to manage database's tables and data (versioned scripts are located in `app/src/main/resources/db/migration` folder).

The application code is agnostic of what relational database is used, so for the production deployment, it will be easy to swap for another database.

### H2 console
Once the application is running, it is possible to access [H2 admin console](localhost:10000/telecom/h2-console) to query/update H2 database.

Parameters to use in H2 admin console:
* JDBC URL: jdbc:h2:mem:telecom
* username: sa
* password: password

## Spring Profiles
Following spring profiles are defined in the project code:
* local - configuration to run application locally
* h2 - to use in-memory H2 database
* test - test profile

When running application locally, need to specify `spring.profiles.active=local,h2`.

When running tests, need to specify `spring.profiles.active=test,h2`.

When another database type is used, a new configuration should be created for the new database type, 
and `h2` profile should be swapped with the profile of the new database.


# Further Improvements

## Pagination improvement
There is no constraint on how pig the page size can be requested. It should be limited to a reasonable number. 
If page size requested is too big, the request should be denied.

## Phone number activation
It is reasonable to assume, that activating phone number involves more than just updating a database field. 
The application should provide async capability (queue, events) to accept an activation request, and let
potentially multiple systems react to it. 

In this case the `activate a phone number` API may return `201 Accepted` to indicate the result of the activation is not known yet. 


