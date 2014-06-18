package com.theladders.reporting;

import com.theladders.job.application.Application;
import com.theladders.job.application.display.Display;

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
    display.write(numberOfApplications);
  }
}
