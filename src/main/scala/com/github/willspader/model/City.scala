package com.github.willspader.model

import com.github.willspader.infrastructure.MongoConnection
import reactivemongo.bson.{BSONDocument, BSONDocumentReader, Macros}

import scala.concurrent.{ExecutionContext, Future}


case class City(id: Long, name: String, state: Option[String], country: String, coord: Coord)

case class Coord(lon: Double, lat: Double)

object CityWriterReader {

  private implicit def cityReader: BSONDocumentReader[City] = Macros.reader[City]

  private implicit def coordReader: BSONDocumentReader[Coord] = Macros.reader[Coord]

  def getCity(name: String)(implicit mongoConnection: MongoConnection, ec: ExecutionContext): Future[Option[City]] = {
    val query = BSONDocument("name" -> name)

    mongoConnection.getCollection(CITY_COLLECTION).flatMap(_.find(query).one[City])
  }

}
