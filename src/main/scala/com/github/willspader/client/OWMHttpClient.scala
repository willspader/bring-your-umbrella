package com.github.willspader.client

import com.github.willspader.`type`.Types.WeatherResponse
import org.json4s.DefaultFormats
import org.json4s.native.Serialization
import sttp.client._
import sttp.client.json4s._

object OWMHttpClient {

  private val apiKey: String = "cbaa77c761f1dc0be6d2ca030eafb3d2"

  def getDailyWeather: Identity[Response[Either[ResponseError[Exception], WeatherResponse]]] = {

    implicit val serialization: Serialization.type = org.json4s.native.Serialization
    implicit val formats: DefaultFormats.type = org.json4s.DefaultFormats
    implicit val backend: SttpBackend[Identity, Nothing, NothingT] = HttpURLConnectionBackend()

    // TODO: API URL IN CONFIG FILE
    basicRequest.get(uri"https://api.openweathermap.org/data/2.5/onecall?lat=-30.03306&lon=-51.23&appid=$apiKey")
      .response(asJson[WeatherResponse])
      .send()
  }

}
