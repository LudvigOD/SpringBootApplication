# Backend Server User Guide

Welcome! This guide explains how to start and stop the backend server using a simple script. If you're responsible for managing the server, these instructions will help you keep things running smoothly.




### Prerequisites
- Java 17 or higher
- Gradle 7.x
- PostgreSQL (for backend)

## Building the Project

Build and package the entire system:
```bash
./gradlew assembleAll
```
This will create `release/project-bundle.zip` containing all necessary components.

The zip file will be placed in `release/project-bundle.zip`. If you unpack it,
you should be able to run the backend server with the included `runner` script,
and the registration application is a runnable jar file. So:

```bash
# Unpack the release zip file
cd release
unzip project-bundle.zip
cd project-bundle

# Start the backend server
./runner start

# Run the registration application
java -jar register.jar

## Running the System

1. **Unpack the release**:
```bash
cd release
unzip project-bundle.zip
cd project-bundle
```

2. **Start the backend server**:
```bash
./runner start
```

3. **Start the registration application**:
```bash
java -jar register.jar
```

4. **Start the admin interface**:
```bash
java -jar admin.jar
```

## Documentation

For detailed technical documentation and architecture overview, see:
- The readME:s in the different directories.
