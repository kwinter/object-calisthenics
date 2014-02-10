package com.theladders.jobseeker;

import com.theladders.job.Job;
import com.theladders.job.application.display.Display;
import com.theladders.job.ats.AtsJob;
import com.theladders.job.jreq.JReq;
import com.theladders.job.jreq.JReqApplicationInProgress;
import com.theladders.jobseeker.job.JobFolder;

public class Jobseeker
{
  private final Name name;
  private final JobFolder jobFolder = JobFolder.empty();

  public Jobseeker(Name name)
  {
    this.name = name;
  }

  public JReqApplicationInProgress applyTo(JReq job)
  {
    return job.applicationFor(this);
  }

  public void applyTo(AtsJob job)
  {
    job.submitApplicationBy(this);
    jobFolder.addApplied(job);
  }

  public void displayNameOn(Display display)
  {
    name.displayOn(display);
  }

  public void save(Job job)
  {
    jobFolder.addSaved(job);
  }
  public void addToAppliedJobs(Job job)
  {
    jobFolder.addApplied(job);
  }

  public void displaySavedJobsOn(Display display)
  {
    jobFolder.displaySavedJobsOn(display);
  }

  public void displayAppliedJobsOn(Display display)
  {
    jobFolder.displayAppliedJobsOn(display);
  }

}
