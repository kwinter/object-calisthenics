package com.theladders.reporting;

import com.theladders.job.Job;
import com.theladders.job.application.display.Display;

public class CountApplicationsByJob implements JobReporter
{
  private final Display  display;


  public CountApplicationsByJob(Display display)
  {
    this.display = display;
  }

  @Override
  public void report(Job job)
  {
    job.displayOn(display);
    display.writeSeparator();
    ApplicationCounter applicationCounter = new ApplicationCounter();
    job.reportApplicationsOn(applicationCounter);
    applicationCounter.displayOn(display);
    display.newline();
  }
}
