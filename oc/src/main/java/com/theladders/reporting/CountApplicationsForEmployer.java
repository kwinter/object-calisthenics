package com.theladders.reporting;

import com.theladders.job.Job;
import com.theladders.job.application.display.Display;

public class CountApplicationsForEmployer implements JobReporter
{
  ApplicationCounter    applicationCounter = new ApplicationCounter();

  @Override
  public void report(Job job)
  {
    job.reportApplicationsOn(applicationCounter);
  }

  public void displayOn(Display display)
  {
    applicationCounter.displayOn(display);
  }
}
