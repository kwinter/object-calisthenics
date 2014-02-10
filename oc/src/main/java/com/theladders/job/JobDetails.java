package com.theladders.job;

import com.theladders.employer.Employer;
import com.theladders.job.application.display.Display;

public class JobDetails
{
  private final Employer employer;
  private final Title    title;

  public JobDetails(Employer employer,
                    Title title)
  {
    this.employer = employer;
    this.title = title;
  }

  public void displayOn(Display display)
  {
    employer.displayNameOn(display);
    display.writeSeparator();
    title.displayOn(display);
  }
}
