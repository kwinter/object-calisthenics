package com.theladders.reporting.application.bydate;

import java.util.Calendar;
import java.util.Date;

import com.theladders.job.application.Applicant;
import com.theladders.job.application.reporting.ApplicationReporter;
import com.theladders.reporting.Display;

public class DisplayApplicant implements ApplicationReporter
{
  private final Date    requestedDate;
  private final Display display;

  public DisplayApplicant(Date date,
                          Display display)
  {
    this.requestedDate = date;
    this.display = display;
  }

  @Override
  public void report(Applicant applicant,
                     Date applicationDate)
  {
    if (sameDay(requestedDate, applicationDate))
    {
      displayJobseeker(applicant);
    }
  }

  private void displayJobseeker(Applicant applicant)
  {
    display.startRow();
    applicant.displayJobseekerOn(display);
    display.endRow();
  }

  // TODO(kw): move this to another class
  private boolean sameDay(Date firstDate,
                          Date secondDate)
  {
    Calendar cal1 = Calendar.getInstance();
    Calendar cal2 = Calendar.getInstance();
    cal1.setTime(firstDate);
    cal2.setTime(secondDate);
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
  }
}
