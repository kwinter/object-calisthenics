package com.theladders.reporting.application.byjob;

import com.theladders.job.Job;
import com.theladders.reporting.Display;
import com.theladders.reporting.Reporter;
import com.theladders.reporting.application.ApplicationCounter;

public class CountApplicationsByJob implements Reporter<Job>
{
  private final Display  display;


  public CountApplicationsByJob(Display display)
  {
    this.display = display;
  }

  @Override
  public void report(Job job)
  {
    display.startRow();
    job.displayOn(display);
    display.writeSeparator();
    displayApplicationCountFor(job);

    display.endRow();
  }

  private void displayApplicationCountFor(Job job)
  {
    ApplicationCounter applicationCounter = new ApplicationCounter();
    job.reportApplicationsOn(applicationCounter);
    applicationCounter.displayOn(display);
  }
}
