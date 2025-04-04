# Sparrow - Bus Booking System

## About The Project

Sparrow is a comprehensive bus booking system developed in Java with MySQL database integration. This project demonstrates object-oriented programming principles and database management skills.

## Features

- **User Management**
  - User registration and authentication
  - Secure password handling
  - User profile management

- **Bus Booking System**
  - Multiple bus types available:
    - Express Bus
    - Luxury Bus
    - AC Sleeper
    - Non-AC Sleeper
  - Dynamic fare calculation based on distance
  - Seat availability checking
  - Booking confirmation

- **Route Management**
  - Multiple routes support
  - Date-based booking
  - Source and destination selection

## Technical Stack

- **Programming Language**: Java
- **Database**: MySQL
- **Database Driver**: JDBC

## Prerequisites

- Java Development Kit (JDK)
- MySQL Server
- MySQL JDBC Driver

## Setup Instructions

1. Clone the repository
2. Set up MySQL database:
   - Create a database named `UserData`
   - Configure database connection in `src/Dataase.java`
3. Add MySQL JDBC driver to the project's classpath
4. Compile and run the project

## Project Structure

```
src/
├── Sparrow.java      # Main application class
├── Dataase.java      # Database operations
├── seats.java        # Seat management
└── d.csv            # Data file
```

## Usage

1. Run the application
2. Choose from available options:
   - New User Registration
   - User Login
   - Book Tickets
   - View Bookings
   - Exit

## Database Schema

The system uses the following main tables:
- UserData: Stores user information
- listOfSeats: Manages seat availability for different bus types

## Contributing

This project was developed as part of an academic assignment to showcase OOP concepts and database management skills.

## License

This project is for educational purposes only.

## Getting Started

In this project, We made a bus booking system named the Sparrow To achieve this functionality we used JAVA and MySQL. 
We made this project to showcase our object-oriented programming skill   

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.


## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).
#   S p a r r o w 
 
 
