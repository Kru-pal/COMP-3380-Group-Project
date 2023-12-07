# COMP-3380-Group-Project

This is a group project for the Databases (Comp 3380) course at the University of Manitoba.

## Project Overview

The project is a Java application designed to connect to a SQL Server database for managing songs.

## Setup

Before running this project, ensure you have Java and SQL Server installed on your machine.

### Instructions:

1. **Compile the Java Files:**
   - Open your terminal.
   - Navigate to the project directory.
   - Run the following commands to compile the necessary Java files:

     javac csvToSql.java
     javac SetUPConnection.java
     javac -cp ".:mssql-jdbc-11.2.0.jre18.jar" SongsDB.java


2. **Load SQL Database:**
   - Ensure your SQL Server is running.
   - Execute the SQL script (`songsSQL.sql`) on the uranium server. 
     - Make changes in the `auth.cfg` file according to your credentials.

3. **Run the Java Program:**
   - After the SQL database is loaded, run the Java program using the following command:

     java -cp ".:mssql-jdbc-11.2.0.jre18.jar" SongsDB

### Note:
- The project assumes you have the `mssql-jdbc-11.2.0.jre18.jar` file in the project directory.
- Ensure proper configuration in `auth.cfg` for SQL Server credentials.
- Alternate to compiling the java files you could run the make command which will compile the files for you but you need to load database (Step 2)
