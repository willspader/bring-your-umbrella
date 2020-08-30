package com.github.willspader.model

import com.github.willspader.infrastructure.MongoConnection
import reactivemongo.api.Cursor
import reactivemongo.bson.{BSONDocument, BSONDocumentReader, Macros}

import scala.concurrent.{ExecutionContext, Future}


case class User(name: String, city: String, coord: Coord)

case class Coord(lon: String, lat: String)

class UserWriterReader {

  private implicit def cityReader: BSONDocumentReader[User] = Macros.reader[User]

  private implicit def coordReader: BSONDocumentReader[Coord] = Macros.reader[Coord]

  def findRegisteredUsers()(implicit mongoConnection: MongoConnection, ec: ExecutionContext): Future[List[User]] = {
    val query = BSONDocument()

    mongoConnection.getCollection(USER_COLLECTION).flatMap(_.find(query)
      .cursor[User]()
      .collect[List](-1, Cursor.FailOnError[List[User]]())
    )
  }

}
