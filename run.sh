echo "Loading $1 into mongodb"

awk '{print NR "," $0}' $1 | mongoimport --db lineserver --collection lines --type csv -f lineNumber,value

echo "Starting web application"

cd scala-spray
sbt run

