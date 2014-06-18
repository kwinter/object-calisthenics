package com.theladders.job.ats;

import java.util.Date;

import com.theladders.employer.Employer;
import com.theladders.job.Job;
import com.theladders.job.JobDetails;
import com.theladders.job.Title;
import com.theladders.job.application.Applicant;
import com.theladders.job.application.Application;
import com.theladders.job.application.Applications;
import com.theladders.job.application.display.Display;
import com.theladders.jobseeker.Jobseeker;
import com.theladders.jobseeker.resume.Resume.NoResume;
import com.theladders.reporting.Reporter;

public class AtsJob implements Job
{
  private final Applications applications = Applications.empty();
  private final JobDetails jobDetails;

  public static AtsJob from(Employer employer,
                            Title title)
  {
    return new AtsJob(new JobDetails(employer, title));
  }

  private AtsJob(JobDetails jobDetails)
  {
    this.jobDetails = jobDetails;
  }

  public void submitApplicationBy(Jobseeker jobseeker)
  {
    // TODO(kw): NoResume vs ValidResume should be a hidden detail from clients - they should just
    // pass Resume
    applications.add(new Application(new Date(), new Applicant(jobseeker, new NoResume())));
  }

  @Override
  public void displayApplicationsOn(Display display)
  {
    applications.displayOn(display);
  }

  @Override
  public void displayOn(Display display)
  {
    jobDetails.displayOn(display);
  }

  @Override
  public void reportApplicationsOn(Reporter<Application> reporter)
  {
    applications.reportOn(reporter);
  }
}
