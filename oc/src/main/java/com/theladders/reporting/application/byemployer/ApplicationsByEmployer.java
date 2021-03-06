package com.theladders.reporting.application.byemployer;

import com.theladders.employer.Employer;
import com.theladders.reporting.Display;
import com.theladders.reporting.Reporter;

public class ApplicationsByEmployer implements Reporter<Employer>
{
  private final Display display;

  public ApplicationsByEmployer(Display display)
  {
    this.display = display;
  }

  @Override
  public void report(Employer employer)
  {
    display.startRow();
    employer.displayNameOn(display);
    display.writeSeparator();
    displayApplicationCountFor(employer);

    display.endRow();
  }

  private void displayApplicationCountFor(Employer employer)
  {
    CountApplicationsForEmployer applicationCounts = new CountApplicationsForEmployer();
    employer.reportJobsOn(applicationCounts);
    applicationCounts.displayOn(display);
  }
}
