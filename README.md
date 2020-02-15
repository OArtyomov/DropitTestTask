## Dropit Delivery service

The service which responsible for manage deliveries, packages

## Setup environment (Ubuntu specific)
1. Setup docker locally
    1.1. In case of problems with docker during build run "sudo chmod 666 /var/run/docker.sock"
    

2. Setup postgreSQL
    5.1  Setup database dropitdeliverydb.
    5.2  Create user ' create user dropit with encrypted password 'dropit''
    5.3 Use this script as example
    
    create database dropitdeliverydb;
    create user dropit with encrypted password 'dropit';
    grant all privileges on database dropitDeliveryDB to dropit;

## Building service
3. Run build with gradle
```
   ./gradlew clean  build
```

## Running service
4. Run a service. We have the following options to run the application:

```
   ./gradlew bootRun
```

or

```
  java -jar build/libs/DropitTestTask.jar
```