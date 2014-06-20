package com.theladders.reporting.application.bydate;

import com.theladders.employer.Employer;
import com.theladders.job.Job;
import com.theladders.reporting.Reporter;

public class ApplicantsByDate implements Reporter<Employer>
{
  private final Reporter<Job> jobReporter;

  public ApplicantsByDate(Reporter<Job> jobReporter)
  {
    this.jobReporter = jobReporter;
  }
  @Override
  public void report(Employer employer)
  {
    employer.reportJobsOn(jobReporter);
  }
}
