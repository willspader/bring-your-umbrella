package com.github.willspader.client

import com.github.willspader.`type`.Types.WeatherResponse

import sttp.client._
import sttp.client.json4s._

import org.json4s.DefaultFormats
import org.json4s.native.Serialization


class OWMHttpClient {

  private val apiKey: String = "cbaa77c761f1dc0be6d2ca030eafb3d2"

  // asJson[T] from sttp.client.json4s
  private implicit val serialization: Serialization.type = org.json4s.native.Serialization

  // asJson[T] from sttp.client.json4s
  private implicit val formats: DefaultFormats.type = org.json4s.DefaultFormats

  // send() from sttp.client
  private implicit val backend: SttpBackend[Identity, Nothing, NothingT] = HttpURLConnectionBackend()

  def getDailyWeather(lat: String, lon: String): Identity[Response[Either[ResponseError[Exception], WeatherResponse]]] = {

    // TODO: API URL IN CONFIG FILE
    basicRequest.get(uri"https://api.openweathermap.org/data/2.5/onecall?lat=$lat&lon=$lon&appid=$apiKey")
      .response(asJson[WeatherResponse])
      .send()
  }

}
