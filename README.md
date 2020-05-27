# MyLibrary
An example app, using Play Framework 2.7, Slick 3.3, that enables user to record details of books owned/read


- RESTful API that uses CRUD operations to persist data into H2 in-memory database.
- Uses Slick for for database query and access mapping because of it's easy PLAY integration and unlike Doobie, it provides type-safety giving us compile-time query checking
- It shows how to handle situations where various database operations have to be performed within a single transaction.
- Easy to read and understand

Run sbt ~run for continuous recompilation of the server app.
Done: http://localhost:9000/
