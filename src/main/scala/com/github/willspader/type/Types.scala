package com.github.willspader.`type`

object Types {

  // daily -> daily list
  case class WeatherResponse(hourly: List[WeatherInfo])

  // dt -> Time of the forecasted data, Unix, UTC
  case class WeatherInfo(dt: Long, weather: List[Weather])

  // main -> Group of weather parameters (Rain, Snow, Extreme etc.)
  // description -> Weather condition within the group
  case class Weather(main: String, description: String)

}
