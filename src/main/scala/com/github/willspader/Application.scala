package com.github.willspader

import com.github.willspader.infrastructure.MongoConnection
import org.quartz.{CronScheduleBuilder, JobBuilder, JobDetail, Scheduler, Trigger, TriggerBuilder}
import org.quartz.impl.StdSchedulerFactory
import com.github.willspader.job.WeatherJob

import scala.concurrent.ExecutionContext.Implicits.global
import com.github.willspader.model.{City, CityWriterReader}

import scala.concurrent.Future
import scala.util.{Failure, Success}


// TODO: === V1.0 ===
// TODO: v1 -> list of cities {id, name, country, coord {lat, lon}} in a database - DONE
// TODO: Some messaging mechanism implementation for the notification (like Twilio)


object Application {

  def main(args: Array[String]): Unit = {

    /*val ec = scala.concurrent.ExecutionContext.Implicits.global

    val mongoConnection: MongoConnection = MongoConnection()

    val futureMaybeCity: Future[Option[City]] = CityWriterReader.getCity("Porto Alegre")(mongoConnection, ec)

    futureMaybeCity.onComplete {
      case Success(maybeCity) => print(maybeCity)
      case Failure(exception) => exception.printStackTrace()
    }*/

    // OWM
    // https://openweathermap.org/api/one-call-api

    // it creates the Weather job
    val weatherJob: JobDetail = JobBuilder.newJob(classOf[WeatherJob]).withIdentity("weather-job").build()

    // trigger w/ the cron expression for the scheduler
    // the cron expression -> 0 */15 * ? * * <- means at second :00, every 15 minutes starting at minute :00, of every hour
    val weatherTrigger: Trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule("0 13 19 ? * * *")).build()

    // sync the job w/ given trigger
    val scheduler: Scheduler = new StdSchedulerFactory().getScheduler
    scheduler.start()
    scheduler.scheduleJob(weatherJob, weatherTrigger)
  }

}
