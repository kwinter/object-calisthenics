package com.theladders.job.jreq;

import com.theladders.employer.Employer;
import com.theladders.job.Job;
import com.theladders.job.JobDetails;
import com.theladders.job.Title;
import com.theladders.job.application.Applicant;
import com.theladders.job.application.Application;
import com.theladders.job.application.Applications;
import com.theladders.job.application.reporting.ApplicationReporter;
import com.theladders.jobseeker.Jobseeker;
import com.theladders.jobseeker.resume.Resume.ValidResume;
import com.theladders.reporting.Display;
import com.theladders.time.Clock;

public class JReq implements Job
{
  private final Applications applications = Applications.empty();
  private final JobDetails jobDetails;

  public static JReq from(Employer employer,
                          Title title)
  {
    return new JReq(new JobDetails(employer, title));
  }

  public JReq(JobDetails jobDetails)
  {
    this.jobDetails = jobDetails;
  }

  public JReqApplicationInProgress applicationFor(Jobseeker jobseeker)
  {
    return new JReqApplicationInProgress(jobseeker, this);
  }

  public void submitApplicationFor(Jobseeker jobseeker,
                                   ValidResume resume)
  {
    applications.add(new Application(Clock.now(), new Applicant(jobseeker, resume)));
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
  public void reportApplicationsOn(ApplicationReporter reporter)
  {
    applications.reportOn(reporter);
  }

}
