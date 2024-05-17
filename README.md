# URL Shortener

## Description
URL Shortener is a simple, yet powerful service that provides short aliases for redirection of long URLs. Utilizing Java 21, Spring Boot 3.2.4, and Gradle, this project offers a robust backend solution for managing URL redirections efficiently.

## Features
- **Create Short URLs**: Generate short links from long URLs in one easy step.
- **Redirection**: Redirects users to the original URL when the short URL is accessed.
- **Analytics**: Track the number of times a short URL has been accessed.

## Requirements
- Java JDK 21
- Gradle 8.x
- Spring Boot 3.2.4
- MongoDb

## Installation

### Clone the repository
```bash
git clone https://github.com/sulimarbernal/ShortLinker.git
cd ShortLinker
```

## Running the API
To run the API, execute the following command in the root directory of the project:

### Environment required
```
MONGO_URI=mongodb://user:pass@localhost:27017/admin 
BASE_URL=https://example.com //  will be use as short url domain
```

Clean the project (optional):
```bash
./gradlew clean
```

Build the project: This step compiles your project and creates the executable JAR.
```bash
./gradlew build
```
Run the application: To start the Spring Boot application, use the bootRun command which runs the project as an application.
```bash
gradle bootRun
```

Run tests:
```bash
./gradlew test
```

Run Jacoco test coverage report (optional):
```bash
./gradlew jacocoTestReport
```


## API Endpoints
### Create a New Short URL
To create a new short URL, make a POST request to the following endpoint:
```bash
curl -X POST http://localhost:8080/shortUrl/ \
-H "Content-Type: application/json" \
-d '{"longUrl": "https://example.com", "status": "false"}'
```

### Redirect from Short to Long URL
To redirect to the original long URL using the short URL, make a GET request to the following endpoint. Replace `{shortUrl}` with the actual short URL identifier provided after creating a short URL:
```bash
curl http://localhost:8080/{shortCodeUrl}
```

### Update ShortUrl entity for update status or longUrl
To update the original long URL or the status of a `ShortUrl` record, make a PUT request to the following endpoint. Replace `{id}` with the actual identifier of the short URL record you want to update:
```bash
curl -X PUT http://localhost:8080/{id} \
-H "Content-Type: application/json" \
-d '{"longUrl": "https://example.com", "status": "false"}'
```

## API Monitoring

### get metrics
To get information on metrics score
```bash
curl http://localhost:8080/actuator/prometheus
```


## Propose to challenge

![img.png](img.png)
![img_1.png](img_1.png)
note = cache not implemented






