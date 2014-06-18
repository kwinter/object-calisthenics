package com.theladders.job.application;

import java.util.Date;

import com.theladders.reporting.Display;

public class Application
{
  private final Date      date;
  private final Applicant applicant;

  public Application(Date date,
                     Applicant applicant)
  {
    this.date = date;
    this.applicant = applicant;
  }

  public void displayOn(Display display)
  {
    display.writeApplicationDate(date);
    display.writeSeparator();
    applicant.displayOn(display);
  }

}
