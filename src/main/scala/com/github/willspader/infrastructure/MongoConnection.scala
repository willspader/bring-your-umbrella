package com.github.willspader.infrastructure

import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.api.{AsyncDriver, DefaultDB, MongoConnection => ReactiveConnection}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


object SharedMongoConnection {

  // as stated for reactivemongo docs
  //
  // ```
  // Creation Costs:
  //
  // MongoDriver and MongoConnection involve creation costs:
  //
  //  - driver creates a new actor system;
  //  - connection will connect to the servers (creating network channels).
  //
  // It is also a good idea to store the driver and connection instances to reuse them.
  //
  // On the contrary, DefaultDB and Collection are just plain objects that store references
  // and nothing else. Getting such references is lightweight, and calling connection.database(..)
  // or db.collection(..) may be done many times without any performance hit.
  // ```
  // for more information checkout http://reactivemongo.org/releases/0.11/documentation/tutorial/connect-database.html

  private lazy val mongoURI: String = "mongodb://localhost:27017"

  private lazy val driver: AsyncDriver = AsyncDriver()

  private lazy val parsedURI = Future.fromTry(ReactiveConnection parseURI mongoURI)

  private lazy val conn = parsedURI.flatMap(driver.connect)

  def withConfig(): Future[ReactiveConnection] = {
    conn
  }

}

case class MongoConnection() {

  private lazy val dbName = "bring_your_umbrella_db"
  private lazy val sharedConnection = SharedMongoConnection.withConfig()

  private def database(): Future[DefaultDB] = {
    sharedConnection.flatMap(_.database(dbName))
  }

  def getCollection(name: String): Future[BSONCollection] = {
    database().map(_.collection(name))
  }

}
