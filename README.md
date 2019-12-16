## Overview

A utility to connect to different databases(MySQL, SQLite, Oracle, Sqlserver) 

---
## Compatibility

### Prerequisites & Supporting Software
None

---
## Configuration

### Adding Local Maven Dependencies for oracle
Due to the binary license there is no public repository with Oracle Driver JAR, so we need to add local maven dependencies.
The local maven repository is by default in  <user.home>/.m2/repository
```
For Windows: c:\Users\<user>\.m2\repository
For Unix or OSX: ~/.m2/repository
```
### Adding Artifacts to Local Maven Repository
Go to the above mentioned directory and open command prompt.To install a JAR in the local repository use the command below where the group-id is com.jda.jci.models.gs1 and artifact-id is gs1-jda-2019.2.0-model and version is 2019.2.0:
```
mvn install:install-file -Dfile=<path-to-file> -DgroupId=<group-id> \
    -DartifactId=<artifact-id> -Dversion=<version> -Dpackaging=<packaging>
```

## How to run on Anypoint Studio

1. Right-click on the app in project explorer
2. Select Run As -> Mule Application(Configure)
3.Click Run.

## Usage:

console can be made use of at http://localhost:8083/index 

## How to build deployable archive that will be published to exchange
Use the command below to get the jar file.
```
mvn clean install
```
## How to publish to exchange
1. Right-click on the app in project explorer
2. Click on option Anypoint Platform and from dropdown menu select the option Publish to Exchange.
