package com.theladders.jobseeker.job;

import com.theladders.job.Job;
import com.theladders.job.Jobs;
import com.theladders.job.application.display.Display;

public class JobFolder
{
  private final Jobs saved   = Jobs.empty();
  private final Jobs applied = Jobs.empty();

  public static JobFolder empty()
  {
    return new JobFolder();
  }

  public void addSaved(Job job)
  {
    saved.add(job);
  }

  public void addApplied(Job job)
  {
    applied.add(job);
  }

  public void displaySavedJobsOn(Display display)
  {
    saved.displayOn(display);
  }

  public void displayAppliedJobsOn(Display display)
  {
    applied.displayOn(display);
  }
}
