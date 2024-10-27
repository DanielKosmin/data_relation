# Data Relation Project

Microservice project to interact with a postgres database to relate transactional information to banking account
records. Technologies/Configurations used will include:

- Java
- Springboot
- Gradle
- PostgreSQL
- Docker
- Docker Compose
- GitHub Actions
- Makefile

### General Project Commands

#### Gradle Build

- this is one of the first commands that should be run to ensure all dependencies are installed correctly
- ensure your [application-local](#step-1-create-an-application-local-yml) is setup prior to building
- this will also ensure the [precommit hook](scripts/pre-commit) is set up correctly

```bash
./gradlew clean build
```

#### Unit Tests

- this will run all unit tests in the project
- This also runs as part of the [precommit hook](scripts/pre-commit)

```bash
./gradlew unitTest
```

#### Integration Tests

- this will run the entire project test suite

```bash
./gradlew test 
```

#### Checkstyle

- Checkstyle is a code analysis tool used to ensure objective Java standards are being met
- This also runs as part of the [precommit hook](scripts/pre-commit)

```bash
./gradlew checkstyleMain checkstyleTest
```

#### Spotless

- Spotless is a code formatting plugin for gradle. In this specific project, google java format is used to auto format
  as part of the build process
- This also runs as part of the [precommit hook](scripts/pre-commit)

```bash
./gradlew spotlessApply
```

### Initialize the project with data to begin interacting with it

##### Step 1 create an application-local-yml

- in `src/main/resources`, create a file called `application-local.yml` and add the following content

```bash 
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/your_database_name
    username: your_username
    password: your_password
```

- this will connect your local project to the local postgres container pulled in the docker compose
- this will currently be connecting to the test container created in the `docker-compose`
- this file is not checked into git, so replacing the contents with actual Postgres DB credentials poses no risks

#### Step 2: Run make to initialize the project

**IMPORTANT**

- this will create a completely new instance and do the following:
    - ensure docker-compose instance of postgres is up
    - drop the existing tables of the database and recreate the tables
    - fill the tables with [test data](http/insert)
- you will be able to interact with the project locally from a clean slate
- this will also ensure the test container db is up for integration tests to run locally

```bash
make init
```

#### Step 3: Tear down the environment

- to kill the local postgres container instance, run the following command

```bash
make clean
```