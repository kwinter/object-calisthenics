package com.theladders.reporting.application;

import java.util.Date;

import com.theladders.job.application.Applicant;
import com.theladders.job.application.reporting.ApplicationReporter;
import com.theladders.reporting.Display;

public class ApplicationCounter implements ApplicationReporter
{
  private int numberOfApplications = 0;

  @Override
  public void report(Applicant applicant,
                     Date date)
{
    numberOfApplications++;
  }

  public void displayOn(Display display)
  {
    display.writeNumberOfApplications(numberOfApplications);
  }
}
