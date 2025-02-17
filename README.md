Välkommen team 7 och kund. Hoppas denna readMe finner er väl.

Get ready to rumble!

## Directory Overview
```
├── admin/ # Admin application for race management
  ├── dto/ # Representation of different data values
  ├── model/ # Admin logic according to MVC pattern
  ├── view/ # Admin GUI
  ├── AdminApplication # Starting point of the admin application
  ├── test/ # Tests for admin application
├── backend/ # Backend server handling data and API
  ├── config/ # Security configuration
  ├── controller/ # End-point definition 
  ├── entity/ # Representation of tables in database (Participant & Times tables)
  ├── exception/ # Custom program exceptions
  ├── repository/ # Springboot query integration
  ├── service/ # Middle-man logic handling in between repository & controller
  ├── BackendApplicatation # Starting point of the backend application
  ├── resources/ # Mock data & application properties
  ├── test/ # Tests for backend application
├── docs/ # Main documenation such as technical documentation, user manual & release information
├── register/ # Time registration application
  ├── RegisterApplication # Starting point for register application
  ├── model/ # Register logic according to MVC pattern
  ├── utils/ # Helper files
  ├── view/ # Register GUI
  ├── test/ # Tests for register application
├── scripts/ # Bash scripts
└── shared/ # Shared components between applications
  ├── dto/ # Representation of different data values
  ├── file/ # Methods for reading & writing files
  ├── gui/ # Helper methods for GUI
  ├── Utils # Program utilities
  ├── tests/ # Tests for utils
├── wiki/ # Extra documentation
```

## System Overview

The race timing system consists of three main components:
1. Registration Application (Register)
2. Administration Application (Admin)
3. Backend Server