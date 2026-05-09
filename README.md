# Project Name: Aquarium Maintenance Management App

## Project Description
The Aquarium Maintenance Management App is a Java console application designed to help manage customer aquarium maintenance accounts. The application allows a user to add, view, search, update, and delete customer service records. Each customer account includes contact information, assigned maintenance worker, service frequency, monthly price, maintenance notes, and linked aquarium tank information such as tank type, tank size, and water type.

The application uses a SQLite database to store customer account and tank information. The database includes two related tables, CustomerAccounts and Tanks, connected through a foreign key relationship.

## Project Tasks
- **Task 1: Set up the development environment**
  - Install and configure Java extensions for VS Code
  - Configure Git and GitHub repository
  - Confirm Java and SQLite JDBC setup

- **Task 2: Design the application**
  - Create the project proposal
  - Define the purpose and output of the application
  - Plan customer account and tank data storage
  - Create class definitions and database table structure

- **Task 3: Develop the application structure**
  - Create the main App class
  - Create customer account and tank classes
  - Create shared account structure using an abstract Account class
  - Create a Serviceable interface for service-related behavior

- **Task 4: Connect to a database**
  - Implement SQLite database connection
  - Create the Tanks table
  - Create the CustomerAccounts table
  - Connect customer accounts to tank records using a foreign key

- **Task 5: Implement create and read operations**
  - Add new customer accounts
  - Store linked tank information
  - View all customer accounts
  - Display account summaries and tank details

- **Task 6: Implement search, update, and delete operations**
  - Search customer accounts by customer name
  - Update customer account information
  - Update linked tank information
  - Delete customer accounts and related tank records

- **Task 7: Add validation and error handling**
  - Validate whole number input
  - Validate decimal input
  - Handle database connection errors
  - Use transaction handling with commit and rollback

- **Task 8: Test the application**
  - Test adding customer records
  - Test viewing all customer records
  - Test searching by customer name
  - Test updating customer and tank information
  - Test deleting customer records
  - Debug and fix database or input issues

- **Task 9: Document the project**
  - Create a complete README file
  - Document the project purpose and development process
  - Explain the programming languages and tools used
  - Prepare the project for GitHub submission

## Project Skills Learned
- Java console application development
- Object-oriented programming
- Abstract class usage
- Interface implementation
- Composition using linked objects
- SQLite database management
- CRUD database operations
- PreparedStatement usage
- Generated key handling
- Transaction handling with commit and rollback
- Input validation
- Git and GitHub version control
- Writing project documentation

## Language Used
- **Java**: Used to create the console application, classes, menu system, and program logic
- **SQL**: Used to create tables and perform database operations
- **SQLite**: Used as the database system for storing customer account and tank records

## Development Process Used
The project was developed in stages. First, the application idea was created as a project proposal based on the need for an organized aquarium maintenance account system. Next, the application design was planned by identifying the main classes, data fields, database tables, and expected program output.

After the design phase, the Java classes were created. The App class was used to run the menu system, while CustomerAccount and Tank stored the main customer and aquarium data. The Account abstract class and Serviceable interface were added to support object-oriented design.

The database phase added SQLite support through a database connection class and a DAO class. The DAO class was used to separate database operations from the main application logic. CRUD operations were implemented so the user could add, view, search, update, and delete customer records.

The final release improved the update feature so that both customer account information and linked tank details can be updated from the menu. This allows the full account record to be maintained more accurately. The application was tested through the console by running each menu option and confirming that the SQLite database stored and updated the correct information.