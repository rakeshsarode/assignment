# coding assignment
coding assignment

# solution approach

As per the requirement, we have to populate the events details in the db and flag the events that takes more than 4ms, and to calculate the event duration for each event id we need both the entries (started, and finished) together.

to pull both the events together we can just sort that file based on line content, but if the file is too big we can use the below approach.

1) split input file into multiple files,
2) sort each file individually,
3) merge all the sorted files in single file,
4) split the sorted merged file into multiple files, 
5) populate the db with all the events from each splitted file from step 4


### Requirements

- Make sure the java 8 and maven is already installed on the machine

### Run the project

- Make sure to be in the root directory

- To run the program below command can be used, with a valid fullpath to logfile.txt
  
  `mvn compile exec:java -Dexec.mainClass="com.cs.assignment.Main" -Dexec.args="full_path_tolog_file"`
  
  Note : please make sure you provide a valid path in exec.args
  
  Example:
  
  `mvn compile exec:java -Dexec.mainClass="com.cs.assignment.Main" -Dexec.args="C:\logfile.txt"`

- once the program is finished, to view the db records below command can be used 
  
  `mvn compile exec:java -Dexec.mainClass="org.hsqldb.util.DatabaseManagerSwing" -Dexec.args="--url jdbc:hsqldb:file:./data/db/events;shutdown=true"`

- log path from root directory

  `./logs/assignment.log`
  
- db files path from root directory

  `./data/db/events`
  
- temp files path from root directory (application will use this folder for temporary work, like sorting and merging)
  
   `./temp`
