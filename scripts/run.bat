@echo off
REM Doc2FAQ Application Startup Script for Windows
REM This script runs the Doc2FAQ application with bundled JRE or system Java

setlocal enabledelayedexpansion

REM Get the directory where this script is located
set "SCRIPT_DIR=%~dp0"
set "APP_DIR=%SCRIPT_DIR%.."

REM Application configuration
set "APP_NAME=Doc2FAQ"
set "JAR_FILE=%APP_DIR%\lib\doc2faq-1.0.0.jar"
set "BUNDLED_JRE=%APP_DIR%\jre"
set "CONFIG_DIR=%APP_DIR%\config"

REM JVM options
set "JVM_OPTS=-Xms256m -Xmx512m -XX:+UseG1GC -XX:+UseStringDeduplication"

REM Spring Boot options
set "SPRING_OPTS=--spring.config.location=classpath:/application.yml,file:%CONFIG_DIR%/"

echo Starting %APP_NAME%...
echo Application directory: %APP_DIR%

REM Check if JAR file exists
if not exist "%JAR_FILE%" (
    echo Error: JAR file not found at %JAR_FILE%
    pause
    exit /b 1
)

REM Determine Java executable to use
set "JAVA_CMD="

REM First, try bundled JRE
if exist "%BUNDLED_JRE%\bin\java.exe" (
    set "JAVA_CMD=%BUNDLED_JRE%\bin\java.exe"
    echo Using bundled JRE: !JAVA_CMD!
    goto :java_found
)

REM If no bundled JRE, try JAVA_HOME
if defined JAVA_HOME (
    if exist "%JAVA_HOME%\bin\java.exe" (
        set "JAVA_CMD=%JAVA_HOME%\bin\java.exe"
        echo Using JAVA_HOME: !JAVA_CMD!
        goto :java_found
    )
)

REM Finally, try system java
java -version >nul 2>&1
if !errorlevel! equ 0 (
    set "JAVA_CMD=java"
    echo Using system Java: !JAVA_CMD!
    goto :java_found
)

echo Error: No Java installation found!
echo Please install Java 17 or later, or use the bundled JRE version.
pause
exit /b 1

:java_found

REM Create logs directory if it doesn't exist
if not exist "%APP_DIR%\logs" mkdir "%APP_DIR%\logs"

echo Starting application with command:
echo "!JAVA_CMD!" %JVM_OPTS% -jar "%JAR_FILE%" %SPRING_OPTS%
echo.
echo Application will be available at: http://localhost:8080
echo Press Ctrl+C to stop the application
echo.

REM Start the application
"!JAVA_CMD!" %JVM_OPTS% -jar "%JAR_FILE%" %SPRING_OPTS%

if !errorlevel! neq 0 (
    echo Error: Application failed to start. Check the logs for details.
    pause
    exit /b 1
)

pause