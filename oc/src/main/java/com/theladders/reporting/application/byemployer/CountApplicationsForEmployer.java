package com.theladders.reporting.application.byemployer;

import com.theladders.job.Job;
import com.theladders.reporting.Display;
import com.theladders.reporting.Reporter;
import com.theladders.reporting.application.ApplicationCounter;

public class CountApplicationsForEmployer implements Reporter<Job>
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
