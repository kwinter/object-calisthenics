package com.theladders.jobseeker;

import com.theladders.reporting.Display;

public class Name
{
  private final String name;

  public Name(String name)
  {
    this.name = name;
  }

  public void displayOn(Display display)
  {
    display.writeJobseekerName(name);
  }
}
