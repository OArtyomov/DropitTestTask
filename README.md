## Dropit Delivery service

The service which responsible for manage deliveries, packages

## Setup environment (Ubuntu specific)
* Setup docker locally
    * In case of problems with docker during build run "sudo chmod 666 /var/run/docker.sock"
    

* Setup postgreSQL
    *  Setup database dropitdeliverydb.
    *  Create user ' create user dropit with encrypted password 'dropit''
    * Use this script as example
    
    create database dropitdeliverydb;
    create user dropit with encrypted password 'dropit';
    grant all privileges on database dropitDeliveryDB to dropit;

## Building service
* Run build with gradle
```
   ./gradlew clean  build
```

## Running service
* Run a service. We have the following options to run the application:

```
   ./gradlew bootRun
```

or

```
  java -jar build/libs/DropitTestTask.jar
```