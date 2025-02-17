Välkommen team 7 och kund. Hoppas denna readMe finner er väl.

Get ready to rumble!

## Directory Overview
```
├── admin/ # Admin application for race management
  ├── src/
    ├── main/java/results
      ├── dto/ # Representation of different data values
        ├── CompetitorDTO
        ├── ResultDTO
      ├── model/ # Admin logic according to MVC pattern
        ├── AdminModel
        ├── AdminModelImpl
        ├── AdminModelObserver
        ├── CompetitorsCalculator
        ├── FilteredAdminModel
        ├── OnlyValidTimesAdminModel
        ├── ResultsCalculator
        ├── StationStartNumberAdminModel
      ├── util/ # Utilities for admin
        ├── Observable
        ├── ProxyObservable
      ├── view/ # Admin GUI
        ├── AdminGUI
        ├── CompetitorsTable
        ├── CompetitorsTableModel
        ├── ResultsTable
        ├── ResultsTableModel
        ├── TimesTable
        ├── TimesTableModel
        ├── TimesWindow
      ├── AdminApplication # Starting point of the admin application
    ├── test/java/admin # Tests for admin application

├── backend/ # Backend server handling data and API
  ├── src/
    ├── main/
      ├── java/ 
        ├── config/ # Security configuration
          ├── CustomConfig
          ├── SecurityConfig
        ├── controller/ # End-point definition 
          ├── ParticipantsController
          ├── TimesController
        ├── entity/ # Representation of tables in database (Participant & Times tables)
          ├── Participant
          ├── Time
        ├── exception/ # Custom program exceptions
          ├── InvalidTimeFormat
        ├── repository/ # Springboot query integration
          ├──ParticipantRepository
          ├──TimeRepository
        ├── service/ # Middle-man logic handling in between repository & controller
          ├── ParticipantService
          ├── TimeService
        ├── BackendApplication # Starting point of the backend application
      ├── resources/ # Mock data & application properties
    ├── test/ # Tests for backend application
  ├── README.md # API documentation

├── docs/ # Main documenation 
  ├── README.md # Technical documentation
  ├── ReleaseInfo.txt
  ├── UserManual.md

├── register/ # Time registration application
  ├── src/
    ├── main/
      ├── main/
        ├── RegisterApplication # Starting point for register application
      ├── model/ # Register logic according to MVC pattern
        ├── EndStation
        ├── RegisterModel
        ├── RegisterModelImpl
        ├── StartStation
        ├── StationModel
      ├── view/ # Register GUI
        ├── RegisterGUI
        ├── RegisterView
    ├── test/ # Tests for register application

├── scripts/ # Bash scripts

├── shared/ # Shared components between applications
  ├── src/
    ├── main/
      ├── dto/ # Representation of different data values
        ├── ParticipantDTO
        ├── TimeDTO
        ├── UpdateParticipantDTO
      ├── file/ # Methods for reading & writing files
        ├── FileUtils
      ├── gui/ # Helper methods for GUI
        ├── PlaceHolderTextField
        ├── RegisterFilter
      ├── Utils # Program utilities
    ├── test/ # Tests for utils

├── wiki/ # Extra documentation
```
For information about specific files view the technical documentation, i.e. the README.md in docs

## System Overview
The race timing system consists of three main components:
1. Registration Application (Register)
2. Administration Application (Admin)
3. Backend Server

For information about application interactions and connections view the technical documentation, i.e. the README.md in docs