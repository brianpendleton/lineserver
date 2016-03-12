# lineserver


#####How does your system work? (if not addressed in comments in source)
We start by preprocessing the given text file, inserting the rows into MongoDB.  The format of each MongoDB document is: `{lineNumber: 2, value: "line of the file"}`.  This allows us query quickly by row number using a method like: `db.collection.find({lineNumber: requestedLineNumber})`.  Once the file is processed, we spin up the web server which has a single rest service.  I chose not to feed the file directly from disk


#####How will your system perform with a 1 GB file? a 10 GB file? a 100 GB file?
The mongo preprocessing will scale to the filesize as long as you have the disk space.  The nice thing about MongoDB is that we can use some replication and sharding to handle different use cases as the collections get larger.


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


#####How long did you spend on this exercise? If you had unlimited more time to spend on this, how would you spend it and how would you prioritize each item?
1 hour designing what I wanted to use (lightweight http, mongodb for persistence)

6+ hours researching what technologies to use

2 hours brushing up on Scala syntax

2 hours coding

#####If you were to critique your code, what would you have to say about it?
I don't have tests for the scala pieces yet.  I was playing with ScalaMock to try and mock out the MongoDB interface, but I ended up spending too much time and had to call it quits.
