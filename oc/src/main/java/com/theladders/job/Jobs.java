package com.theladders.job;

import java.util.ArrayList;
import java.util.Collection;

import com.theladders.job.application.display.Display;
import com.theladders.reporting.Reporter;

public class Jobs
{
  private final Collection<Job> jobs;

  public static Jobs empty()
  {
    return new Jobs(new ArrayList<Job>());
  }

  private Jobs(Collection<Job> jobs)
  {
    this.jobs = jobs;
  }

  public void add(Job job)
  {
    jobs.add(job);
  }

  public void displayOn(Display display)
  {
    for (Job job : jobs)
    {
      job.displayOn(display);
      display.newline();
    }
  }

  public void reportOn(Reporter<Job> reporter)
  {
    for (Job job : jobs)
    {
      reporter.report(job);
    }
  }
}
