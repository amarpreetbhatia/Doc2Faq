# Doc2FAQ

## Description
A service that helps you generate FAQs from your documents.

## Features
- 📄 Upload your documents in various formats
- 🤖 AI-powered FAQ generation
- 📝 Easy-to-use interface
- ⚡ Fast processing and results
- 🎨 User-friendly error pages (404, 500)
- 🔧 Thymeleaf template engine integration

## How to Run Locally
To compile and run the application locally, use the following command:

```bash
mvn spring-boot:run
```

The application will be available at `http://localhost:8080`.

## Testing Error Pages
The application includes custom error pages for better user experience:

- **404 Page**: Visit any non-existent URL (e.g., `http://localhost:8080/non-existent-page`)
- **500 Page**: Visit `http://localhost:8080/test/500` (only available in non-production profiles)

## How to Build and Run with Docker

You can build and run the application using Docker:

### 1. Build the Docker image

```bash
docker build -t doc2faq .
```

### 2. Run the Docker container

```bash
docker run -p 8080:8080 --name doc2faq doc2faq
```

The application will be available at `http://localhost:8080`.

## Running Tests

To run all tests:

```bash
mvn test
```

To run specific test categories:

```bash
# Unit tests only
mvn test -Dtest="*Test"

# Integration tests only
mvn test -Dtest="*IntegrationTest"
```

## Technology Stack
- Spring Boot 3.2.0
- Thymeleaf Template Engine
- Maven
- Java 17
- Docker
