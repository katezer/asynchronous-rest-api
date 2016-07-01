#Asynchronous REST API

------
##Guide:
1. Download dependencies, this is a Maven project
2. Start the application
3. Create some long executing object
⋅⋅* URL: http://localhost:8080/create?id=1&name=test (can change ID and NAME)
⋅⋅* Response: {"int":1,"description":"Object under creation, please consult on the referenced URL","url":"/get?id=1"}
4. Go to check it's status with the given URL in the creation response
⋅⋅* URL: http://localhost:8080/get?id=1
⋅⋅*	Response Pending: {"int":1,"description":"Object still under creation, please consult on the referenced URL","url":"/get?id=1"}
5. Wait and recheck again using the same URL
⋅⋅* Response: {"int":1,"name":"test","time":45134}

------
##Magic:
The trick is that the Service method returns a Future and it is tagged with @Async forcing it to be non blocking and return something.
The returned Future is stored in a HashMap and the "get" method synchronously checks if the Future has finished.
The HTTP return is a json with the polling url and a 202 HTTP status meaning ACCEPTED.
The get method at the same time can answer with different HTTP status, ACCEPTED, CREATED and ERROR.

------
##Idea source:
Asynchronous Rest HTTP Status post: http://restcookbook.com/Resources/asynchroneous-operations/
Asynchronous methods with Spring Boot: https://spring.io/guides/gs/async-method/
