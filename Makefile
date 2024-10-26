.PHONY: init start-database run-app populate-db clean wait-for-app

DATA_RELATION_BASE_URL ?= http://localhost:8080

init: start-database run-app create-tables populate-db

start-database:
	@echo "Starting Docker Compose for PostgreSQL..."
	@docker-compose up -d > /dev/null 2>&1

run-app:
	@echo "Building and starting the application..."
	@./gradlew clean bootRun > /dev/null 2>&1 & APP_PID=$$! && echo $$! > app.pid


create-tables: wait-for-app
	@echo "Creating the database tables..."
	@OUTPUT=$$(curl -s -X POST $(DATA_RELATION_BASE_URL)/table_queries/v1/create); \
	echo "$$OUTPUT"; \
	if echo "$$OUTPUT" | grep -q "DB Connection Strings not setup correctly"; then \
		echo "Exiting Makefile due to error: $$OUTPUT"; \
		kill `cat app.pid` || true; \
		rm -f app.pid; \
		exit 1; \
	fi

populate-db:
	@echo "Uploading banking CSV..."
	@curl -X POST $(DATA_RELATION_BASE_URL)/table_queries/v1/insert \
		-H "Content-Type: multipart/form-data" \
		-F "file=@http/insert/checking_records.csv"

	@echo "\nUploading credit CSV..."
	@curl -X POST $(DATA_RELATION_BASE_URL)/table_queries/v1/insert \
		-H "Content-Type: multipart/form-data" \
		-F "file=@http/insert/credit_records.csv"

	@echo "\nData population complete. Shutting down the application..."
	@kill `cat app.pid`
	@rm app.pid

wait-for-app:
	@echo "Waiting for the application to be ready..."
	@until curl -s http://localhost:8080/actuator/health | grep 'OK' > /dev/null; do \
		echo "Waiting..."; \
		sleep 2; \
	done

clean:
	@echo "\nKilling all docker containers..."
	docker stop $$(docker ps -aq) && docker rm $$(docker ps -aq) && docker system prune -a --volumes -f

