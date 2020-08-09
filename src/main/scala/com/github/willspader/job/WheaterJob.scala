package com.github.willspader.job

import com.github.willspader.client.OWMHttpClient
import org.quartz.{Job, JobExecutionContext}

class WheaterJob extends Job {
  override def execute(context: JobExecutionContext): Unit = {
    // TODO: analyze the daily data
    OWMHttpClient.getDailyWheater()
  }
}
