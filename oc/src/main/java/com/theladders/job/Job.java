package com.theladders.job;

import com.theladders.job.application.Application;
import com.theladders.job.application.display.Display;
import com.theladders.reporting.Reporter;

public interface Job
{
  void displayApplicationsOn(Display display);

  void displayOn(Display display);

  void reportApplicationsOn(Reporter<Application> reporter);
}
