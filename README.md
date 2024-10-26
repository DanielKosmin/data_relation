# Data Relation Project

## Initialize the project with data to begin interacting with it

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

#### Step 2: Run make to initialize the project

**IMPORTANT**

- this will create a completely new instance and do the following:
  - ensure docker-compose instance of postgres is up
  - drop the existing tables of the database and recreate the tables
  - fill the tables with [test data](http/insert)
- you will be able to interact with the project locally from a clean slate

```bash
make init
```

#### Step 3: Tear down the environment once completed

- to kill the local postgres container instance, run the following command

```bash
make clean
```