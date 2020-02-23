# Real_estate BE 

# Table of contents

* [Prerequisites](#Prerequisites)
* [Setup for local development](#setup)
* [Status](#status)

## Prerequisites
* JDK 8
* MySQL 5.7

## Setup for local development

### Database
* Install MySQL locally and name database _realestate_

### Backend
* Setup the _application.properties_ like below
```
spring.jpa.hibernate.ddl-auto=update 
spring.datasource.username=DB_USER
spring.datasource.password=DB_PASSWORD
spring.datasource.driverClassName=com.mysql.jdbc.Driver  
```
Variables:
DB_USER => your local database user (e.g. root)
DB_PASSWORD => local database password (e.g. default for root is an empty string)

### Deployment

* Build an executable project (.jar) and run it
```
mvnw clean package
java -jar \target\nwt2_ms_location-0.0.1-SNAPSHOT.jar
```
### How to check if it is working?
After running application, open the database and check whether the tables were created
