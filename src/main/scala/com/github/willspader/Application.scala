package com.github.willspader

import org.quartz.{CronScheduleBuilder, JobBuilder, JobDetail, Scheduler, Trigger, TriggerBuilder}
import org.quartz.impl.StdSchedulerFactory

import com.github.willspader.job.WheaterJob

object Application {

  def main(args: Array[String]): Unit = {

    // it creates the wheater job
    val job: JobDetail = JobBuilder.newJob(classOf[WheaterJob]).withIdentity("wheater-job").build()

    // trigger w/ the cron expression for the scheduler
    // the cron expression 0 30 5 ? * * * means everyday at 05:30 AM
    val trigger: Trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule("0 30 05 ? * * *")).build()

    // sync the job w/ given trigger
    val scheduler: Scheduler = new StdSchedulerFactory().getScheduler
    scheduler.start()
    scheduler.scheduleJob(job, trigger)

    // TODO: HTTP Request to a wheater API
    // TODO: Some messaging mechanism implementation for the notification (like Twilio)
  }

}
