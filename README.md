# Data Relation Project

## Get a Postgres db locally

##### Step 1 clean existing docker resources
```bash 
docker stop $(docker ps -aq) && docker rm $(docker ps -aq) && docker system prune -a --volumes -f
```
#### Step 2: Run Docker Compose to Create a Local PostgreSQL Instance
```bash
docker-compose up -d
```