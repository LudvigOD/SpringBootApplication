# Backend Server User Guide

Welcome! This guide explains how to start and stop the backend server using a simple script. If you're responsible for managing the server, these instructions will help you keep things running smoothly.

## Starting and Stopping the Server

To interact with the server, you’ll use a command-line script that controls when the server is on or off.

### Basic Commands

- **Start the server**: Run the following command in the terminal:

  ```bash
  ./runner start
  ```

  This starts the backend server and creates a log file in the `logs` folder, where it saves important information.

- **Stop the server**: If you need to turn off the server, run:

  ```bash
  ./runner stop
  ```

  This safely shuts down the server and cleans up any temporary files.

- **Restart the server**: To restart the server, you can combine the two commands above:
  ```bash
  ./runner restart
  ```

### Log Management

The server creates a log file to keep track of its activity. Logs are automatically rotated, meaning that when the current log file reaches 50 MB, it’s renamed to `archive.log`, and a new log file starts. This keeps logs manageable and prevents them from taking up too much space.

## Troubleshooting

If you encounter any issues, here are some quick tips to help resolve them:

### "Server Already Running" Message

If you try to start the server and see a message saying it’s already running:

1. If you want to stop or restart the server, use the `stop` or `restart` commands.
1. If the stop command doesn’t work, you might need to troubleshoot further. Keep reading!
1. Make sure the server isn’t currently running by using:

   ```bash
   ps aux | grep 'backend.jar'
   ```

   This will show if there’s already a server process. If there is, you’ll need to stop it before starting a new one. See "Manually Stopping the Server" below.

1. If you’re sure the server isn’t running, check for a leftover PID file:
   ```bash
   ls backend-server.pid
   ```
   If the file is there, delete it and try starting the server again:
   ```bash
   rm backend-server.pid
   ./runner start
   ```

### Manually Stopping the Server

If the `stop` command doesn’t work, you can stop the server manually:

1. Check if the server process is running:

   ```bash
   ps aux | grep 'backend.jar'
   ```

   Example output:

   ```bash
    user 12345 0.0 0.0 12345 6789 ? S 12:34 0:00 java -jar backend.jar
           ^                                                  ^
          this is the PID                            this is the process
   ```

2. Identify the Process ID (PID) for the server (see above).

3. Kill the process using:
   ```bash
   kill -9 <PID>
   ```

Replace `<PID>` with the actual ID number of the process. Once it’s stopped, you should be able to start the server again normally.

---

If you follow these steps, you should be able to control the server without issues. Happy hosting!


## Quick Start
1. Build the project:
```bash
./gradlew build
```

2. Start the backend:
```bash
./gradlew backend:bootRun
```

3. Start the admin interface:
```bash
./gradlew admin:bootRun
```

4. Start the registration interface:
```bash
./gradlew register:bootRun
```

# Race Timing System - Technical Architecture

## Component Details

### 1. Registration Application
- **Purpose**: Records start and finish times for competitors
- **Design Pattern**: MVC (Model-View-Controller)
- **Key Classes**:
  - `RegisterGUI`: View component for time registration
  - `RegisterModel`: Business logic and data handling
  - `StationModel`: Represents start/finish stations

### 2. Administration Application
- **Purpose**: Displays and manages race results
- **Design Pattern**: Observer Pattern, MVC, Template Method
- **Key Classes**:
  - `AdminGUI`: Main interface for result viewing
  - `AdminModel`: Data management and updates
  - `ResultView`: Specialized result visualization

### 3. Backend Server
- **Purpose**: Central data storage and API
- **Design Pattern**: Repository Pattern, Service Pattern
- **Key Components**:
  - REST API endpoints
  - Business logic services
  - Data persistence layer

## Network Communication

- Backend runs on port 8080
- Applications communicate via REST API
- WebClient used for HTTP requests
- Real-time updates using intervall checking of the backend

## Data Flow
1. Register app records times
2. Times sent to backend via POST
3. Admin app polls for updates
4. Results calculated and displayed

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
