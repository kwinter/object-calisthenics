package com.theladders.job.application;

import java.util.ArrayList;
import java.util.Collection;

import com.theladders.job.application.reporting.ApplicationReporter;
import com.theladders.reporting.Display;

public class Applications
{
  private final Collection<Application> applications;

  public static Applications empty()
  {
    return new Applications(new ArrayList<Application>());
  }

  private Applications(Collection<Application> applications)
  {
    this.applications = applications;
  }

  public void add(Application application)
  {
    this.applications.add(application);
  }

  public void displayOn(Display display)
  {
    for (Application application : applications)
    {
      application.displayOn(display);
    }
  }


  public void reportOn(ApplicationReporter reporter)
  {
    for (Application application : applications)
    {
      application.reportOn(reporter);
    }
  }
}
