package com.theladders.job.application;

import com.theladders.job.application.display.Display;
import com.theladders.jobseeker.Jobseeker;
import com.theladders.jobseeker.resume.Resume;

public class Applicant
{
  private final Jobseeker jobseeker;
  private final Resume resume;

  public Applicant(Jobseeker jobseeker,
                   Resume resume)
  {
    this.jobseeker = jobseeker;
    this.resume = resume;
  }

  public void displayOn(Display display)
  {
    jobseeker.displayNameOn(display);
    display.writeSeparator();
    resume.displayOn(display);
  }
}
