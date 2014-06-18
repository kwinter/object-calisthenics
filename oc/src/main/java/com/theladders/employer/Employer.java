package com.theladders.employer;

import com.theladders.job.Job;
import com.theladders.job.Jobs;
import com.theladders.job.Title;
import com.theladders.job.application.display.Display;
import com.theladders.job.application.display.StringWriterDisplay;
import com.theladders.job.ats.AtsJob;
import com.theladders.job.jreq.JReq;
import com.theladders.reporting.Reporter;

public class Employer
{
  private final Name name;
  private final Jobs jobs = Jobs.empty();

  public Employer(Name name)
  {
    this.name = name;
  }

  public JReq createJreqWith(Title title)
  {
    return JReq.from(this, title);
  }

  public AtsJob createAtsJobWith(Title title)
  {
    return AtsJob.from(this, title);
  }

  public void post(Job job)
  {
    jobs.add(job);
  }

  public void displayNameOn(Display display)
  {
    name.displayOn(display);
  }

  public void displayJobsOn(StringWriterDisplay display)
  {
    jobs.displayOn(display);
  }

  public void reportJobsOn(Reporter<Job> reporter)
  {
    jobs.reportOn(reporter);
  }
}
