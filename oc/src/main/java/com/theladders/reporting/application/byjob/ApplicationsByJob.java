package com.theladders.reporting.application.byjob;

import com.theladders.employer.Employer;
import com.theladders.reporting.Display;
import com.theladders.reporting.Reporter;

public class ApplicationsByJob implements Reporter<Employer>
{
  private final Display display;

  public ApplicationsByJob(Display display)
  {
    this.display = display;
  }
  @Override
  public void report(Employer employer)
  {
    CountApplicationsByJob applicationCounts = new CountApplicationsByJob(display);
    employer.reportJobsOn(applicationCounts);
  }
}
