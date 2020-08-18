package com.github.willspader.job

import com.github.willspader.`type`.Types
import com.github.willspader.client.OWMHttpClient
import org.quartz.{Job, JobExecutionContext}
import sttp.client.ResponseError

import java.time.Instant


class WeatherJob extends Job {

  val REPORTS: Int = 12

  override def execute(context: JobExecutionContext): Either[ResponseError[Exception], Boolean] = {
    val body: Either[ResponseError[Exception], Types.WeatherResponse] = OWMHttpClient.getDailyWeather.body

    if (body.isRight) {

      var willRain: Boolean = false

      // zipWithIndex gives us a tuple w/ index and object -> ._1 is the object and ._2 is the index
      body.right.get.hourly.zipWithIndex.foreach(weatherInfo => {

        if (weatherInfo._2 < REPORTS) {
          if ("Rain".equals(weatherInfo._1.weather.head.main)) {
            println(s"Vai chover as ${Instant.ofEpochSecond(weatherInfo._1.dt)}")
            println(s"Description: ${weatherInfo._1.weather.head.main}")
            willRain = true
          }
        }
      })

      if (!willRain) {
        println("It will not rain for the next 12 hours")
      }

      Right(willRain)

    } else {
      Left(body.left.get)
    }
  }
}
