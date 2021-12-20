setup:
	gradle wrapper --gradle-version 7.2

clean:
	./gradlew clean

build:
	./gradlew clean build

start:
	APP_ENV=development ./gradlew run

install:
	./gradlew install

start-dist:
	APP_ENV=production .build/install/Task-Weather-service/bin/Task-Weather-service

generate-migrations:
	./gradlew generateMigrations

test:
	./gradlew test

report:
	./gradlew jacocoTestReport

check-updates:
	./gradlew dependencyUpdates

.PHONY: build