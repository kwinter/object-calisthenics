package com.theladders.jobseeker.resume;

import com.theladders.job.application.display.Display;

public class Title
{
  private final String title;

  public Title(String title)
  {
    this.title = title;
  }

  public void displayOn(Display display)
  {
    display.writeResumeTitle(title);
  }
}
