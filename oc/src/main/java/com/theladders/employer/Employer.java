package com.theladders.employer;

import com.theladders.job.Job;
import com.theladders.job.Jobs;
import com.theladders.job.Title;
import com.theladders.job.ats.AtsJob;
import com.theladders.job.jreq.JReq;
import com.theladders.reporting.Display;
import com.theladders.reporting.Reporter;
import com.theladders.reporting.format.StringWriterDisplay;

public class Employer
{
  private final Name name;
  private final Jobs jobs = Jobs.empty();

  public Employer(Name name)
  {
    this.name = name;
  }

  public JReq postJreqWith(Title title)
  {
    JReq jreq = JReq.from(this, title);
    post(jreq);
    return jreq;
  }

  public AtsJob postAtsJobWith(Title title)
  {
    AtsJob atsJob = AtsJob.from(this, title);
    post(atsJob);
    return atsJob;
  }

  private void post(Job job)
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
