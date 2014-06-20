package com.theladders.job;

import com.theladders.job.application.reporting.ApplicationReporter;
import com.theladders.reporting.Display;

public interface Job
{
  void displayApplicationsOn(Display display);

  void displayOn(Display display);

  void reportApplicationsOn(ApplicationReporter reporter);
}
