package com.theladders.reporting;

import com.theladders.employer.Employer;
import com.theladders.job.application.display.Display;

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
