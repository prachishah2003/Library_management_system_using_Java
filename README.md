<h1 align="center">
    <br>
    Library Management System Using Java
    <br>
</h1>



A full stack application using Spring Boot, Angular and PostgreSQL (Apache Maven, Hibernate,Bootstrap). 
* This is a Library Management System with an Admin and a User side for the application. 
* Admin can perform CRUD with books/users. 
* User can borrow and return a book. 
* Uses JWT to authenticate login.
* Uses BCryptPasswordEncoder to encrypt the password stored in the database.
* Redirects to forbidden page if a role doesn't have access to the url.
<br>

# APIs

## Authenticate
* Post Mapping to return JWT.
```
{
    username: "user",
    password: "password"
}
```

## Book
* Get Mapping to find all books in the database.
* Get Mapping to find book by id provided.
* Post Mapping to create book.
```
{
    "bookName": "Book Name",
    "bookAuthor": "Author Name",
    "bookGenre": "Genre",
    "noOfCopies": 5
}
```
* Put Mapping to edit book.
```
{
    "bookName": "New Book Name",
    "bookAuthor": "Author Name",
    "bookGenre": "Genre",
    "noOfCopies": 7
}
```
* Delete Mapping to delete a book.

## User
* Get Mapping to find all users in the database.
* Get Mapping to find user by id provided.
* Post Mapping to create user.
```
{
    "username": "user",
    "name": "First User",
    "password": "password",
    "role": [
        {
            "roleName": "Admin"
        }
    ]
}
```
* Put Mapping to edit user.
```
{
    "username": "user",
    "name": "New First User",
    "password": "password",
    "role": [
        {
            "roleName": "User"
        }
    ]
}
```

## Borrow
* Get Mapping to find all transactions taken place.
* Get Mapping to find list of books borrowed by a user.
* Get Mapping to find list of users who have borrowed a particular book.
* Post Mapping to borrow a book.
```
{
    "bookId": 3,
    "userId": 5
}
```
* Post Mapping to return a book.
```
{
    "borrowId": 1
}
```



# Application Properties
```
server.port=port_number

spring.datasource.url=jdbc:postgresql://localhost:5432/database_name
spring.datasource.username=username
spring.datasource.password=password
spring.datasource.driver-class-name=org.postgresql.Driver

# Hibernate settings
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

# Development
* Frontend
```
npm install
```
* Backend
```
mvn install
```

# Build
* Frontend
```
ng serve
```

* Backend
```
mvn spring-boot:run
```
