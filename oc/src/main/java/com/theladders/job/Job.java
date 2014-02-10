package com.theladders.job;

import com.theladders.job.application.display.Display;

public interface Job
{
  void displayApplicationsOn(Display display);

  void displayOn(Display display);
}
