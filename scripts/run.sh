#!/bin/bash

# Doc2FAQ Application Startup Script for Linux/macOS
# This script runs the Doc2FAQ application with bundled JRE or system Java

set -e

# Get the directory where this script is located
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
APP_DIR="$(dirname "$SCRIPT_DIR")"

# Application configuration
APP_NAME="Doc2FAQ"
JAR_FILE="$APP_DIR/lib/doc2faq-1.0.0.jar"
BUNDLED_JRE="$APP_DIR/jre"
CONFIG_DIR="$APP_DIR/config"

# JVM options
JVM_OPTS="-Xms256m -Xmx512m -XX:+UseG1GC -XX:+UseStringDeduplication"

# Spring Boot options
SPRING_OPTS="--spring.config.location=classpath:/application.yml,file:$CONFIG_DIR/"

echo "Starting $APP_NAME..."
echo "Application directory: $APP_DIR"

# Check if JAR file exists
if [ ! -f "$JAR_FILE" ]; then
    echo "Error: JAR file not found at $JAR_FILE"
    exit 1
fi

# Determine Java executable to use
JAVA_CMD=""

# First, try bundled JRE
if [ -d "$BUNDLED_JRE" ]; then
    if [ -f "$BUNDLED_JRE/bin/java" ]; then
        JAVA_CMD="$BUNDLED_JRE/bin/java"
        echo "Using bundled JRE: $JAVA_CMD"
    fi
fi

# If no bundled JRE, try JAVA_HOME
if [ -z "$JAVA_CMD" ] && [ -n "$JAVA_HOME" ]; then
    if [ -f "$JAVA_HOME/bin/java" ]; then
        JAVA_CMD="$JAVA_HOME/bin/java"
        echo "Using JAVA_HOME: $JAVA_CMD"
    fi
fi

# Finally, try system java
if [ -z "$JAVA_CMD" ]; then
    if command -v java >/dev/null 2>&1; then
        JAVA_CMD="java"
        echo "Using system Java: $JAVA_CMD"
    else
        echo "Error: No Java installation found!"
        echo "Please install Java 17 or later, or use the bundled JRE version."
        exit 1
    fi
fi

# Check Java version
JAVA_VERSION=$("$JAVA_CMD" -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 17 ]; then
    echo "Warning: Java version $JAVA_VERSION detected. Java 17 or later is recommended."
fi

# Create logs directory if it doesn't exist
mkdir -p "$APP_DIR/logs"

# Set up signal handlers for graceful shutdown
trap 'echo "Shutting down $APP_NAME..."; kill $APP_PID 2>/dev/null; exit 0' SIGTERM SIGINT

echo "Starting application with command:"
echo "$JAVA_CMD $JVM_OPTS -jar $JAR_FILE $SPRING_OPTS"
echo ""
echo "Application will be available at: http://localhost:8080"
echo "Press Ctrl+C to stop the application"
echo ""

# Start the application
"$JAVA_CMD" $JVM_OPTS -jar "$JAR_FILE" $SPRING_OPTS &
APP_PID=$!

# Wait for the application to start
sleep 5

# Check if the application is still running
if ! kill -0 $APP_PID 2>/dev/null; then
    echo "Error: Application failed to start. Check the logs for details."
    exit 1
fi

echo "$APP_NAME started successfully with PID: $APP_PID"

# Wait for the application to finish
wait $APP_PID