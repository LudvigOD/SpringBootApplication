# Technical documentation

Welcome! This documentation explains the technical aspects of the program, such as:
    - What subprograms and files exist and what they do
    - How to build the system and the runnable files
    - What main tasks the subprograms and files have and how they interact
    - How the subprograms and files are distributed over different computers during a competition
    - Internal structure of each subprogram 
    - Design patterns

## Prerequisites
- Java 17 or higher

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
unzip <projectName-projectVersion>.zip
cd <projectName-projectVersion>
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

5. **Access the results webclient**
Go to URL: pvg-projekt.cs.lth.se:8005/results.html

# Race Timing System - Technical Architecture

## Component Details

### 1. Register Application
- **Purpose**: Records start and finish times for competitors
- **Design Pattern**: Observer Pattern, MVC (Model-View-Controller)
- **Key Classes**:
  - `RegisterGUI`: View component for time registration
  - `RegisterModel`: Interface for RegisterModelImpl
  - `RegisterModelImpl`: Action logic and data handling
- **Other Classes**:
  - `EndStation`: Represents an end station
  - `StationModel`: Represents stations
  - `StartStation`: Represents a start station
  - `RegisterView`: Interface for GUI

### 2. Admin Application
- **Purpose**: Displays and manages race results
- **Design Pattern**: Observer Pattern, MVC, Template Method, Strategy Pattern
- **Key Classes**:
  - `AdminGUI`: Main interface for result viewing
  - `AdminModel`: Data management and updates
  - `ResultView`: Specialized result visualization
  - `AdminModelImpl`: Action logic and data handling
  - `CompetitorsTable`: Displays table sorted by start number
  - `ResultsTable`: Displays table sorted by placement
  - `TimesTable`: Displays table with latest post request
- **Other Classes**:
  - `CompetitorDTO`: Representation of data values for participant with time values in database
  - `ResultDTO`: Gives additional information about the results of a participant in a competition
  - `AdminModelObserver`: Interface for updating data in admin based on updates in the backend
  - `CompetitorsCalculator`: Aggregates competitor data from time and participant values
  - `FilteredAdminModel`: Filters results based on either placement or start number
  - `OnlyValidTimesAdminModel`: Displays only times whose participants exists
  - `ResultsCalculator`: Calculate race results by aggregating competitor data
  - `StationStarNumberAdminModel`: Provides only those Times whose Station ID and Start number match the given values
  - `Observable`: Interface for internal observer pattern use
  - `ProxyObservable`: Provides an abstraction layer for managing observer registration
  - `CompetitorsTableModel`: Contains data displayed in CompetitorsTable
  - `ResultsTableModel`: Contains data displayed in ResultsTable
  - `TimesTableModel`: Contains data displayed in TimesTable
  - `TimesWindow`: Displays TimesTable in new window

### 3. Backend Application
- **Purpose**: Central data storage and API
- **Key Classes**:
  - `ParticipantsController`: REST API endpoints for participants
  - `TimesController`: REST API endpoints for times
  - `Participant`: Represents participants in the database
  - `Time`: Represents times in the database
  - `ParticipantRepository`: Access point to database for the participant entity
  - `TimeRepository`: Access point to database for the time entity
  - `ParticipantService`: Adds logic to database operations and links ParticipantRepository with ParticipantsController 
  - `TimeService`: Adds logic to database operations and links TimeRepository with TimesController 

- **Other Classes**:
  - `CustomConfig`: Custom configurations
  - `SecurityConfig`: Security configuration for testing
  - `InvalidTimeFormat`: Custom time exceptions

- For API documentation view the README.md file stored in the backend folder

### 4. Shared files for all applications
- **Classes**
  - `RegisterFilter`: Filters user input
  - `ParticipantDTO`:  Representation of data values for Participant 
  - `TimeDTO`: Representation of data values for Time
  - `UpdateParticipantDTO`: Updates start number and name for ParticipantDTO
  - `FileUtils`: Methods for reading & writing files
  - `PlaceholderTextField`: A JTextField with a placeholder text that is displayed when the field is empty
  - `Utils`: Program utilities

# Communication and interaction
  
## Network Communication
- Backend runs on port 8080 (configurable)
- Applications communicate via REST API
- WebClient used for HTTP requests
- Updates using intervall checking of the backend

## Data Flow
1. Register app records times
2. Times sent to backend via POST
3. Admin app polls for updates
4. Results calculated and displayed

## Program and file interactions
- Visualization of file interaction for admin: https://miro.com/app/board/uXjVIe94A1k=/?share_link_id=452366861689
- Visualization of program interaction: https://miro.com/app/board/uXjVIeZ7XHE=/?share_link_id=289366039900

### Text overview of communication between Register & Backend
1. RegisterApplication creates RegisterView & RegisterModelImpl
2. User registration input in RegisterView is sent through RegisterModelImpl to the controllers in the backend, in the form of TimeDTOs
3. The controllers call the respective services in regards to operations, which in turn use the respective repositories

### Text overview of communication between Admin & Backend
1. AdminApplication creates AdminGUI & AdminModelImpl
2. User input is sent through AdminGUI, which in turn switches to the corresponding table
3. AdminApplication calls the AdminModelImpl in intervals to fetch data using a GET request from the restserver
4. AdminModelImpl then updates AdminGUI with new data from the server 

## Distribution of programs and files across multiple computers during a competition
It is possible to run multiple stand-alone register applications at once, they all connect to the restserver.
It is possible to run multiple stand-alone admin applications at once, they do not share the same states but are all connected to the restserver.
The program allows for only one server to run at a time.
