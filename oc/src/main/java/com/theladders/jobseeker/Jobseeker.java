package com.theladders.jobseeker;

import com.theladders.job.Job;
import com.theladders.job.ats.AtsJob;
import com.theladders.job.jreq.JReq;
import com.theladders.job.jreq.JReqApplicationInProgress;
import com.theladders.jobseeker.job.JobFolder;
import com.theladders.jobseeker.resume.Resume.ValidResume;
import com.theladders.jobseeker.resume.Title;
import com.theladders.reporting.Display;

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
    job.acceptApplicationFor(this);
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

  public ValidResume createResumeWith(Title title)
  {
    return new ValidResume(this, title);
  }

}
