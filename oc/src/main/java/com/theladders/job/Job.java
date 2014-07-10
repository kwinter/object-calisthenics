package com.theladders.job;

import com.theladders.job.application.reporting.ApplicationReporter;
import com.theladders.jobseeker.Jobseeker;
import com.theladders.jobseeker.resume.Resume.ValidResume;
import com.theladders.reporting.Display;

public interface Job
{
  void acceptApplicationFor(Jobseeker jobseeker);

  void acceptApplicationFor(Jobseeker jobseeker,
                            ValidResume resume);
  
  void displayApplicationsOn(Display display);

  void displayOn(Display display);

  void reportApplicationsOn(ApplicationReporter reporter);
}
