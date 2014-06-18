package com.theladders;

import com.theladders.employer.Employer;
import com.theladders.employer.Employers;
import com.theladders.employer.Name;
import com.theladders.job.application.display.Display;
import com.theladders.reporting.ApplicationsByEmployerAndJob;

public class TheLadders
{
  private final Employers employers = new Employers();

  public Employer createEmployerWith(Name name)
  {
    Employer employer = new Employer(name);
    employers.add(employer);
    return employer;
  }

  public void reportApplicationsByEmployerAndJobOn(Display display)
  {
    ApplicationsByEmployerAndJob applicationsByEmployerAndJob = new ApplicationsByEmployerAndJob(display);
    employers.reportWith(applicationsByEmployerAndJob);
  }
}
