package com.theladders.jobseeker;

import com.theladders.job.application.display.Display;

public class Name
{
  private final String name;

  public Name(String name)
  {
    this.name = name;
  }

  public void displayOn(Display display)
  {
    display.write(name);
  }
}
