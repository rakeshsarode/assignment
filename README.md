# coding assignment
coding assignment


### Requirements

- Make sure the java 8 and maven is already installed on the machine

### Run the project

- Make sure to be in the root directory

- To run the program below command can be used, with a valid fullpath to logfile.txt
  
  `mvn compile exec:java -Dexec.mainClass="com.assignment.Main" -Dexec.args="full_path_tolog_file"`
  
  Note : please make sure you provide a valid path in exec.args
  
  Example:
  
  `mvn compile exec:java -Dexec.mainClass="com.assignment.Main" -Dexec.args="C:\logfile.txt"`

- once the program is finished, to view the db records below command can be used 
  
  `mvn compile exec:java -Dexec.mainClass="org.hsqldb.util.DatabaseManagerSwing" -Dexec.args="--url jdbc:hsqldb:file:./data/db/events;shutdown=true"`

- log path from root directory

  `./logs/assignment.log`
  
- db files path from root directory

  `./data/db/events`
  
- temp files path from root directory (application will use this folder for temporary work, like sorting and merging)
  
   `./temp`