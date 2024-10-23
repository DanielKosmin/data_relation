# Data Relation Project

## Get a Postgres db locally

- run `docker stop $(docker ps -aq) && docker rm $(docker ps -aq) && docker system prune -a --volumes -f` to clean up
  docker env
- run `docker-compose up -d` to create the DB locally