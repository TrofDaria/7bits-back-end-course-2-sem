create-db-and-user:
	sudo -u postgres psql -f create-user-and-db.sql

build:
	mvn clean package

test:
	mvn test

start: build run

run:
	java -jar ./target/w3-postgresql-*.jar