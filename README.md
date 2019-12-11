# Jerry's Final Project Description

## Database:
If you want to run it on your machine, you have to execute sql files under static/SQL.
Also, modify the database.property in /input, set your database connection info.


## Table description
- Hotel: storing all information about hotels, primary key its hotel id.
- Review: storing reviews provided by users, review id as primary key , and user_id as foreign key from User.
- User: storing user's info, the password will be encrypted.
- User_Saved_Hotel: storing the hotels saved by users, which row combination is unique.


## Back-end
My final project mimics the Spring boot structure.
- Controller: Dispatching request and request handling.
- Service: All logics
- Dao: Read and Update from/to database
- Dependency Injection: 

### Controller:
MyController class dispatches different URL to different servlets.

A servlet is responsible for parsing the request to object-data and passing the data to services.
Then, after receiving the return from service, it responses json or html with velocity.

### Service
All logical operations are provided by services such as encrypted, produce salt, calculate rating, etc.  
There are few caches object that make the data searching faster.  
Services also call the dao to update the data in database.

### Dao
No one can access the database except for the dao. Dao dedicates read and update data to the database.  
In my project, all operations are wriiten as stored procedures.  
It makes every operation faster(because they have been compiled in DB), and more maintainable

### Dependency Injection
In Spring we have **@Bean annotation to implement dependency injection.
But basically I made a SingletonServices to create static services, and all controller access these static service to make my project Thread-safe.

## Google Api
I hard code the google map api on my project, I will close it in few days. So you may replace it to make the map and attractions service work.
