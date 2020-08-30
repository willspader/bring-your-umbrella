package com.github.willspader

import org.quartz.{CronScheduleBuilder, JobBuilder, JobDetail, Scheduler, Trigger, TriggerBuilder}
import org.quartz.impl.StdSchedulerFactory
import com.github.willspader.job.WeatherJob


object Application {

  def main(args: Array[String]): Unit = {

    // it creates the Weather job
    val weatherJob: JobDetail = JobBuilder.newJob(classOf[WeatherJob]).withIdentity("weather-job").build()

    // trigger w/ the cron expression for the scheduler
    // the cron expression -> 0 */15 * ? * * <- means at second :00, every 15 minutes starting at minute :00, of every hour
    val weatherTrigger: Trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule("0 20 22 ? * * *")).build()

    // sync the job w/ given trigger
    val scheduler: Scheduler = new StdSchedulerFactory().getScheduler
    scheduler.start()
    scheduler.scheduleJob(weatherJob, weatherTrigger)
  }

}
