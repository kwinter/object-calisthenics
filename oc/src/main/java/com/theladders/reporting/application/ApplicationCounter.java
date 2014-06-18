package com.theladders.reporting.application;

import com.theladders.job.application.Application;
import com.theladders.reporting.Display;
import com.theladders.reporting.Reporter;

public class ApplicationCounter implements Reporter<Application>
{
  private int numberOfApplications = 0;

  @Override
  public void report(Application application)
{
    numberOfApplications++;
  }

  public void displayOn(Display display)
  {
    display.writeNumberOfApplications(numberOfApplications);
  }
}
