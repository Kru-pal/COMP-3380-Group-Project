# Define variables
JC = javac
JVM = java
JFLAGS = -cp ".:mssql-jdbc-11.2.0.jre18.jar"

# Default target
all: setUPConnection csvToSql songsDB 

# Rule to compile SetUPConnection.java
setUPConnection: SetUPConnection.java
	$(JC) SetUPConnection.java

# Rule to compile and run csvToSql.java
csvToSql: csvToSql.java
	$(JC) csvToSql.java
	$(JVM) csvToSql

# Rule to compile and run SongsDB.java
songsDB: SongsDB.java
	$(JC) $(JFLAGS) SongsDB.java

# Clean rule to remove compiled class files
clean:
	rm -f *.class
