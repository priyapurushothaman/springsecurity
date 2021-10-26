## Environment:
- Java version: 1.8
- Maven version: 3.*
- Spring Boot version: 2.2.1.RELEASE

## Read-Only Files:
- src/test/*


## Requirements:
There are two parts that need to be addressed for this question, the authentication of incoming requests and the authorization of the user who has triggered the request.
`Authentication` deals with identifying who is trying to access the API - student (or) office admin.
`Authorization` on the other hand deals with the verifying that a particular user has access to a particular resource, in this case, that would be either course (or) student resource.

While securing the endpoints,

* session management needs to be STATELESS
* CSRF needs to be disabled 

You have to secure the following provided REST endpoints.

`POST` request to `/course`:
* this operation can only be authorized to the user having role OFFICE_ADMIN
* if any non OFFICE_ADMIN user tries to perform this operation, this should result in access denied error and return `ApiResponse` as a response body with statusCode `403` and message `Authorization Failure-This user does not have the sufficient level of access`
* if authentication fails, return `ApiResponse` as a response body with statusCode `401` and message `Authentication Failure-The user name and password combination is incorrect`

`POST` request to `/student`:
* this operation can be authorized to both OFFICE_ADMIN and STUDENT_USER roles
* if any non (OFFICE_ADMIN or STUDENT_USER) user tries to perform this operation, this should result in access denied error and return `ApiResponse` as a response body with statusCode `403` and message `Authorization Failure-This user does not have the sufficient level of access`
* if authentication fails, return `ApiResponse` as a response body with statusCode `401` and message `Authentication Failure-The user name and password combination is incorrect`


`GET` request to `/course`:
* this operation should not need any authentication and authorization

The authentication and authorization features in this question are handled using the `HTTP Basic Authentication`, and for any given request to be authenticated a header `"Authorization: Basic {encoded user and password}"` is passed, generally, in a full-fledged web application, this password would be encoded, but for the scope of the question, the password will be a plain text string. Note that authentication should be followed by authorization, but not the other way around, so configure security configuration accordingly.
 
Your task is to complete the implementation of `WebSecurityConfig` class so that it passes all the test cases when running the provided unit tests.
 
Note that the user name and password combination have been configured as in-memory values in `WebSecurityConfig.java`, you just have to handle the part of defining the authentication and authorization.

## Commands
- run: 
```bash
mvn clean package; java -jar target/springsecurity-1.0.jar
```
- install: 
```bash
mvn clean install
```
- test: 
```bash
mvn clean test
```
