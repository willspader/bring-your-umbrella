package com.github.willspader.job

import com.github.willspader.`type`.Types
import com.github.willspader.client.OWMHttpClient
import org.quartz.{Job, JobExecutionContext}
import sttp.client.ResponseError
import java.time.Instant

import com.github.willspader.infrastructure.MongoConnection
import com.github.willspader.model.{User, UserWriterReader}

import scala.concurrent.Future
import scala.util.{Failure, Success}


class WeatherJob extends Job {

  private val owmhttpClient: OWMHttpClient = new OWMHttpClient
  private val userWriterReader: UserWriterReader = new UserWriterReader

  private val ec = scala.concurrent.ExecutionContext.Implicits.global
  private val mongoConnection: MongoConnection = MongoConnection()

  private val REPORTS: Int = 12

  override def execute(context: JobExecutionContext): Unit = {

    val eventualUsers: Future[List[User]] = userWriterReader.findRegisteredUsers()(mongoConnection, ec)

    eventualUsers.onComplete {
      case Success(users) =>
        users.foreach(user => {
          val body: Either[ResponseError[Exception], Types.WeatherResponse] = owmhttpClient.getDailyWeather(user.coord.lat, user.coord.lon).body

          if (body.isRight) {
            var willRain: Boolean = false

            println(s" Usuario: ${user.name} - Cidade: ${user.city}")
            body.right.get.hourly.zipWithIndex.foreach(weatherInfo => {
              if (weatherInfo._2 < REPORTS) {
                if ("Rain".equals(weatherInfo._1.weather.head.main)) {
                  println(s"Vai chover as ${Instant.ofEpochSecond(weatherInfo._1.dt)}")
                  println(s"Description: ${weatherInfo._1.weather.head.main}")
                  willRain = true
                }
              }

              if (!willRain) {
                println("It will not rain for the next 12 hours")
              }
            })
          } else {
            print(body.left)
          }
        })
      case Failure(exception) => exception.printStackTrace()
    }(ec)
  }
}
