I want to create a new microservice. First lets plan the tasks. So far I have created a repo git@github.com:bmenaman/java-ms.git and checked it out to /Users/roger/repos/bmenaman/java-ms.

## Requirements:
I want to use gradle for the build system.

Build tools & libraries:
    java 21
    the latest compatible versions of:
    - pitest
    - junit
    - jacoco
    - jib
    - some style and formating library - I'm not sure which one yet.
    - cucumber

    compile time dependencies:
    - spring boot 
    - dagger 
    - log4j
    - kafka client

    test dependencies
    - in-memory kafka

The project should have a simple rest api with a single endpoint functioning as a health check.  There should be an adder service that  adds  numbers received via kafka messages and emits the running total via kafka.  This should be tested with cucumber, the cucumber tests should rely on the in-memory kafka.  They should have unit test coverage. Logging should be demonstrated.  There should be a test script to ping the health check endpoint.

## Task:
Write a detailed plan that an AI can follow to complete the task
 first I'd suggest verifying the environment.  then a trivial java gradle setup with a basic unit test. Then the container and health check. The output should be file java-ms-task-list.md.  For each stage include a concrete action to verfify the task.  

Ways of working:
verifying each step.  Where possible create a failing test first.



