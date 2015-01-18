IntroSDE@Unitn third assignment delivery
========================================
@Author Mirko Morandi ID 176043

This file will simply guide you in the usage of the application, a more detailed
description of how the application works is present in the source code.

#1 The Database

The database is contained into the file "database.sqlite" using sqlite3 as DBMS. The db is made
of 4 (5 counting the sqlite_seq) tables which represents:Person, Measure,MeasureType.
The script for generating the database can be found under the folder extra/.

#2 Running the server

The server is placed under the directory server/
First run ivy to download all the necessary libraries, then ant compile to compile.
Once compiling has finished launch the following command to run the server:
	
	ant execute.runServer

	if everything is ok you should see the following message:

	 [echo] Running server
     [java] Starting People Service...
     [java] --> Published at = http://localhost:443/mirko-morandi/ws/people

Note: if the server is going to be launched on MAC OS X use sudo because the port 443 needs sudo permissions to be used.

#3 Running the client

The client is placed under the directory client/
As before launch ivy and ant compile
To start the client use the following commands

	ant execute.launchClient -Durl="http://localhost:443/mirko-morandi/ws/people?wsdl"

And it will start all the 12 tests on the requirements plus the requests content.
	
=================

That's all folks
