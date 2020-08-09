package com.github.willspader.job

import org.quartz.{Job, JobExecutionContext}

class WheaterJob extends Job {
  override def execute(context: JobExecutionContext): Unit = {
    println("oi")
  }
}
