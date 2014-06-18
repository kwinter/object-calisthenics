package com.theladders;

import com.theladders.employer.Employer;
import com.theladders.employer.Employers;
import com.theladders.employer.Name;
import com.theladders.job.application.display.Display;
import com.theladders.reporting.Reporter;
import com.theladders.reporting.application.byemployer.ApplicationsByEmployer;
import com.theladders.reporting.application.byjob.ApplicationsByJob;

public class TheLadders
{
  private final Employers employers = new Employers();

  public Employer createEmployerWith(Name name)
  {
    Employer employer = new Employer(name);
    employers.add(employer);
    return employer;
  }

  public void reportApplicationsByJobOn(Display display)
  {
    display.startSection();
    reportWith(new ApplicationsByJob(display));
    display.endSection();
  }

  public void reportApplicationsByEmployerOn(Display display)
  {
    display.startSection();
    reportWith(new ApplicationsByEmployer(display));
    display.endSection();
  }

  private void reportWith(Reporter<Employer> reporter)
  {
    employers.reportWith(reporter);
  }
}
