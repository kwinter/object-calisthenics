package com.theladders.reporting;

import com.theladders.employer.Employer;
import com.theladders.job.application.display.Display;

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
    employer.displayNameOn(display);
    display.writeSeparator();

    CountApplicationsForEmployer applicationCounts = new CountApplicationsForEmployer();
    employer.reportJobsOn(applicationCounts);
    applicationCounts.displayOn(display);

    display.newline();
  }
}
