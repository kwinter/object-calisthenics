package com.theladders.reporting.application.bydate;

import com.theladders.job.Job;
import com.theladders.job.application.reporting.ApplicationReporter;
import com.theladders.reporting.Reporter;

public class DisplayApplicants implements Reporter<Job>
{
  private final ApplicationReporter applicationReporter;


  public DisplayApplicants(ApplicationReporter reporter)
  {
    this.applicationReporter = reporter;
  }

  @Override
  public void report(Job job)
  {
    displayApplicants(job);
  }

  private void displayApplicants(Job job)
  {
    job.reportApplicationsOn(applicationReporter);
  }
}
