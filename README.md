# IMPORTANT

This repository `master` branch will be blocked on March 1, 2020 due to Revinate private repositories migration to CodeCommit.
This repository will be removed from Github on April 1, 2020.

CodeCommit URL: https://us-west-2.console.aws.amazon.com/codesuite/codecommit/repositories/carter/browse?region=us-west-2

**Please validate**
Prior to migration please ensure that:

1. CodeCommit repository is synced with Github repository. If not, please contact Infra team, who has means to do so.
2. (Optional) If repository has integration with Jenkins please adjust pipeline configuration according to this [documentation](https://revinate.atlassian.net/wiki/spaces/ENG/pages/181403812/Configure+Jenkins+to+poll+CodeCommit+repository)
3. (Optional) If you need advanced integration between CodeCommit and Jenkins pipelines (such as triggering pipeline(s) on PR, branch, tag creation, etc) please contact @jevjay-revinate (jay.jermakov@revinate.com) who will assist you with your pipeline configuration

For more information on how to configure your local git environment to work with CodeCommit please refer to this [documentation](https://revinate.atlassian.net/wiki/spaces/ENG/pages/115441711/Setting+up+local+git+with+CodeCommit)

If you have any additional questions/requests please contact @jevjay-revinate (jay.jermakov@revinate.com)

---

###  Carter
Carter is service, which is used as API for filtering connections, s3 messages,
It provided possible values for filtering.

###  Description

Carter can return dynamic data and static data.

Static data - it is different dimensions for filtering. For example: message types, timezones, directions, endpoint types.
See class DictionariesController,

Dynamic data - results of filtering. It can be connections, accounts, connectionids. See class DataController.

Internally library https://bitbucket.org/gt_tech/spring-data-querydsl-value-operators is used.
Currently this library does not support filtering by boolean fields, but this was done internally in this project.
See package org.bitbucket.gt_tech.spring.data.querydsl.value.operators
https://bitbucket.org/gt_tech/spring-data-querydsl-value-operators/issues/16/support-of-boolean-fields 
- ticket for include this stuff to library. 

###  Setup

The Booter readme has setup instructions for all apps:

[Booter's README](https://github.com/revinate/booter#setup)

You should add to sources of project in IDE next folder:build/generated/source/apt
These classes are generated during build.

###  Development

Make sure your docker-machine is properly configured

Build and run all tests
```
./yoda
```

### Deployment

##### _are there any special deployment instructions?_

Nop

###  Component Testing 
   Kafka and LocalStack(S3 service) in Docker containers are using. All S3 buckets are created during Localstack container startup.
   After finishing all tests all buckets will be dropped. No necessary to run any 3-party services. Just run test from your
   favourite IDE (Intellij IDEA is preferable).
   
### Usage
   Locally application can be run in docker mode and just from IDE.
   If you run from IDE:
     1. ./yoda deps up
     2. Use this properties: -Dkafka.bootstrapServers=localhost:9092 -Dspring.datasource.url=jdbc:mysql://0.0.0.0:3306/carterdb
  If you run as ./yoda appdocker up
     1. No additional actions.
  Important: In both cases all buckets for s3 is created automatically. See class com.revinate.carter.config.S3Configuration           

##### _is there an API or some other way of triggering the service?_
  Service can work for write and read. For test write functionality - send message to suitable Kafka topic
  For read - call any of available API endpoints.
  Available endpoints:
   Dynamic data:
   /v1/remotesystems
   /v1/connectionids/
   /v1/accounts
   /v1/rawmessages/{uuid}
   /v1/transformedinboundmessages/{uuid}
   /v1/transformedoutboundmessages/{uuid}
   /v1/resultmessages/{uuid}
   /v1/publishedmessages/{uuid}
   /v1/messages - parameter ValidatedAt is Date and required.
   /v1/messagesCount - parameter ValidatedAt is Date and required   

   Date format: MM/DD/yyyy hh:mm:ss timezone. For example - 11/11/2019 10:00:00 UTC
   
   Static data:
   /v1/timezones
   /v1/directions
   /v1/authenticationmethods
   /v1/endpointtypes/
   /v1/messagetypes
   
   
##### _How to build valid query for Dynamic data:_
   Lets look on example for /v1/remotesystems
   1. Execute query /v1/remotesystems.
   2. You receive response with list of fields, which are filterable
   3. Execute query with filter /v1/remotesystems?fieldName = eq(5)
   4. Full list of operators: https://bitbucket.org/gt_tech/spring-data-querydsl-value-operators/src/master/ - see section "Value comparison operators"   
   
   
##### _How do I interact with it? What are the endpoints?_
   Also included in the answer to the previous question, the standard
   monitoring endpoints (e.g., "actuator", etc.) can be used to determine
   the health of the service.
    
   
##### _Is authentication required?_
   Basic authentication is required. See properties spring.security.user.name, spring.security.user.password 

###  Help
Run `./yoda help` to learn about the remaining commands