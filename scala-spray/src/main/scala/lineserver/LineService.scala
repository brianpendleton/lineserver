package lineserver

import akka.actor.Actor
import spray.json.DefaultJsonProtocol
import spray.routing._
import spray.http._
import Mongo.Lines

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class LineServiceActor extends Actor with LineService {

  def actorRefFactory = context
  def receive = runRoute(findLineRoute)
}

// This trait defines our service behavior independently from the service actor
trait LineService extends HttpService with DefaultJsonProtocol {

  // The only valid route for our application is /line/xxxxx.
  val findLineRoute =
    pathPrefix("line" / IntNumber) { lineNumber =>
      get {
        complete {
          // When the following line matches, we get back an MongoDBOject with the following structure:
          // Some({ "_id" : ObjectId("56e3a10ea26542b87e8a4621"), "lineNumber" : 20, "value" : "This is row #20" })
          // So we pattern match and then just "get" the value of the line because we don't care about the line number
          Lines.findByLineNumber(lineNumber) match {
            case Some(x) => StatusCodes.OK -> x.get("value").toString()  // If we have a result, then send back HTTP 200 and the line
            case _ => StatusCodes.RequestEntityTooLarge  // If we don't have a result, then the number was not found (out of bounds)
          }
        }
      }
    }
}