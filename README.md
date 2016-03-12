# lineserver


#####How does your system work? (if not addressed in comments in source)
We start by preprocessing the given text file, inserting the rows into MongoDB.  The format of each MongoDB document is: `{lineNumber: 2, value: "line of the file"}`.  This allows us query quickly by row number using a method like: `db.collection.find({lineNumber: requestedLineNumber})`.  Once the file is processed, we spin up the web server which has a single rest service.  I chose not to feed the file directly from disk


#####How will your system perform with a 1 GB file? a 10 GB file? a 100 GB file?
The mongo preprocessing will scale to the filesize as long as you have the disk space.  The nice thing about MongoDB is that we can use some replication and sharding to handle different use cases as the collections get larger.  As the collection grows in size, I think we would also want to index it by rownumber which would help query performance.


#####How will your system perform with 100 users? 10000 users? 1000000 users?
With a lightweight HTTP server, most frameworks should be comfortable handling thousands of requests per second.  MongoDB will be the bottleneck, but that third part tool was built to handle load.  It offers us options for sharding data across multiple instances and using load balancing to direct queries.


#####What documentation, websites, papers, etc did you consult in doing this assignment?

http://www.dropwizard.io/0.9.2/docs/

http://spray.io/

http://reactivemongo.org/

http://blog.mongodb.org/post/77278906988/crittercism-scaling-to-billions-of-requests-per

http://blog.mongodb.org/post/33700094220/how-mongodbs-journaling-works

https://www.khalidalnajjar.com/insert-200-million-rows-into-mongodb-in-minutes/


#####What third-party libraries or other tools does the system use? How did you choose each library or framework you used?
I use MongoDB over trying to server the file myself.  I wanted a tool that would be queryable by line number, and also offer something which could scale (if needed).  I chose the Spray framework for the http server because it was lightweight and I really liked the DSL they built to express the actor workflow for handling requests. Combined with the scala pattern matching, it is simple to understand what the actors do and how they respond with status codes.

#####How long did you spend on this exercise? If you had unlimited more time to spend on this, how would you spend it and how would you prioritize each item?
1 hour designing what I wanted to use (lightweight http, mongodb for persistence)

6+ hours researching what technologies to use

2 hours brushing up on Scala syntax

2 hours coding

#####If you were to critique your code, what would you have to say about it?
I don't have tests.  I am not very fast at Scala as the last time I used it was 18 months ago. I would never release this into production without wrapping the services in tests. I was spending too much time messing with ScalaMock to mock out the MongoDB calls.  I easily could have written a Specification if I was just serving the raw files off disk, but the Mongo hooks made it difficult as the web server relies on mongo running.

The web server relies on MongoDB being installed and running on port 27017 (default). I didn't want to try to install MongoDB in the build.sh file because I don't know what platform it will run on.  So the code requires the user to do something like the following:  https://docs.mongodb.org/manual/tutorial/install-mongodb-on-linux/  Only a few steps, but I didn't want to put it in my script.

The run.sh will use a linux command to take your file and pipe it as a CSV into mongoimport.  This should only be executed once, so if you kill the server and want to rerun it, please comment our the line which bulk imports into MongoDB.  If you don't, it will reinsert the file again.

