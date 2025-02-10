Välkommen Michael Kund! Hoppas denna readMe finner dig väl. 

Get ready to rumble!

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

## Directory Overview
```
├── admin/ # Admin application for race management
├── backend/ # Backend server handling data and API
├── register/ # Time registration application
└── shared/ # Shared components between applications
```

# Race Timing System - Technical Architecture

## System Overview

The race timing system consists of three main components:
1. Registration Application (Register)
2. Administration Application (Admin)
3. Backend Server

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