package com.github.willspader.`type`

object Types {

  // daily -> daily list
  case class WheaterResponse(daily: List[WheaterInfo])

  // dt -> Time of the forecasted data, Unix, UTC
  case class WheaterInfo(dt: String, weather: List[Wheater])

  // main -> Group of weather parameters (Rain, Snow, Extreme etc.)
  // description -> Weather condition within the group
  case class Wheater(main: String, description: String)

}
