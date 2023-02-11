# state-machine

Thank you for taking the time to review this task, I hope it would be to your liking.

I chose to solve the task using purely my implementation for the state machine to ensure delivering the best representation of my skills. However, I know in the scope of this project using spring state may be more efficient, but I had to account for the time limit of this project and that I am doing it on my free time when I'm not on call at work.

You can run this project simply by cloning it and the routing using the terminal/cmd to the directory of the project and executing "docker-compose up"

Testing :
- Unit and Integration testing under "StateMachineApplicationTests.java"
- Manual testing using Swagger at : "http://localhost:8080/state-machine/swagger-ui.html"

Stack :
- Java 11
- Spring boot
- Docker
- MySQL
- Open API

There are three end-points defined,
1) Create Employee
  this end-point consumes a JSON object of type employeeDTO with the following format : 
  {
    "name" : "test-name",
    "age" : 20,
    "contractNumber" : 1234,
    "country" : "test-country"
   }
   I did not define many attributes for the employee entity as per the task description. However, I defined a constraint on the contract number so that the user will not be able to define a new employee with an existing contract number and if that happens, the end point will throw an exception.
   
2) Find Employee By ID
  this end-point takes the employee id as a path variable and returns the user if located and if not it throws a not found exception
  
3) Transition (The most important end-point)
  this end-point takes the employee id as a path variable and the transition the user would like to make as request parameter.
  The available transitions are : 
  - CHECK 
  - ACTIVATE
  - COMPLETE_INITIAL_WORK_PERMIT_CHECK
  - FINISH_WORK_PERMIT_CHECK
  - FINISH_SECURITY_CHECK
  you can use any transition with the same spelling but case in-sensitive and if any unknown transition was sent the user should receive an invalid transition exception.
  also, if any illegal transition was attempted by the user, the app will throw an Illegal transition exception.

  Some exceptions were created specifically for the business need of this task and some pre-existing ones were used, but, all exceptions are handled using a controller advice class to ensure a clear error message for the user.


