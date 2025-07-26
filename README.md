# Doc2FAQ

## Description
A service that helps you generate FAQs from your documents.

## How to Run Locally
To compile and run the application locally, use the following command:

```bash
mvn spring-boot:run
```


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
