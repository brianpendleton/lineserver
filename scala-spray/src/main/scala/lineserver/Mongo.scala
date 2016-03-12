package lineserver

import com.mongodb.casbah.MongoClient
import com.mongodb.casbah.query.Imports._
import com.typesafe.config._


trait Mongo {
  val config = ConfigFactory.load()

  private lazy val client = MongoClient(config.getString("mongodb.server"))
  private lazy val db = client(config.getString("mongodb.database"))
  protected lazy val collection = db(config.getString("mongodb.collection"))

  object Lines {
    def findByLineNumber(lineNum: Int) = collection.findOne("lineNumber" $eq lineNum)
  }
}
object Mongo extends Mongo