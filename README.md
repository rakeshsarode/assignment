# coding assignment
coding assignment


# useful maven commands

to run the program below command can be used, with a valid fullpath to logfile.txt
Note : please make sure you provide a valid path in exec.args
mvn compile exec:java -Dexec.mainClass="com.assignment.Main" -Dexec.args="full_path_tolog_file"
Example: mvn compile exec:java -Dexec.mainClass="com.assignment.Main" -Dexec.args="C:\Users\Aaryahi\cs-assignment\src\main\resources\logfile.txt"

once the program is finished, to view the db records below command can be used 
mvn compile exec:java -Dexec.mainClass="org.hsqldb.util.DatabaseManagerSwing" -Dexec.args="--url jdbc:hsqldb:file:./data/db/events;shutdown=true"
