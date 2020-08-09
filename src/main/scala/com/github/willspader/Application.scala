package com.github.willspader

import org.quartz.{CronScheduleBuilder, JobBuilder, JobDetail, Scheduler, Trigger, TriggerBuilder}
import org.quartz.impl.StdSchedulerFactory
import com.github.willspader.job.WeatherJob

object Application {

  def main(args: Array[String]): Unit = {

    // OWM
    // https://openweathermap.org/api/one-call-api

    // it creates the Weather job
    val job: JobDetail = JobBuilder.newJob(classOf[WeatherJob]).withIdentity("weather-job").build()

    // trigger w/ the cron expression for the scheduler
    // the cron expression 0 30 5 ? * * * means everyday at 05:30 AM
    val trigger: Trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule("0 13 19 ? * * *")).build()

    // sync the job w/ given trigger
    val scheduler: Scheduler = new StdSchedulerFactory().getScheduler
    scheduler.start()
    scheduler.scheduleJob(job, trigger)

    // TODO: Some messaging mechanism implementation for the notification (like Twilio)
  }

}
