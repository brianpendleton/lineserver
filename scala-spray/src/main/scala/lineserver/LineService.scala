package lineserver

import akka.actor.Actor
import com.mongodb.casbah.{MongoCollection, MongoClient}
import com.mongodb.casbah.commons.MongoDBObject
import spray.json.DefaultJsonProtocol
import spray.routing._
import spray.httpx.SprayJsonSupport._
import spray.http._
import MediaTypes._


case class Line(_id: Int, lineNumber: Int, value: String)

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class LineServiceActor extends Actor with LineService {

  val client = MongoClient("localhost", 27017)
  val db = client("test")
  val collection = db("mongo")

  def actorRefFactory = context

  def receive = runRoute(findLineRoute)
}


// this trait defines our service behavior independently from the service actor
trait LineService extends HttpService with DefaultJsonProtocol {
  val collection: MongoCollection

  val findLineRoute = pathPrefix("line" / IntNumber) { lineNumber =>
    get {
      complete {
      //respondWithMediaType(`text/plain`) {
          val lineQuery = MongoDBObject("lineNumber" -> lineNumber)
          val result: String = collection.findOne(lineQuery) match {
            case Some(x) => x.get("value").toString()
            case _ => ""
          }
          result
        }
    }
  }
}