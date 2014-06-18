package com.theladders;

import com.theladders.employer.Employer;
import com.theladders.employer.Employers;
import com.theladders.employer.Name;
import com.theladders.job.application.display.Display;
import com.theladders.reporting.ApplicationsByEmployer;
import com.theladders.reporting.ApplicationsByJob;
import com.theladders.reporting.Reporter;

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
    reportWith(new ApplicationsByJob(display));
  }

  public void reportApplicationsByEmployerOn(Display display)
  {
    reportWith(new ApplicationsByEmployer(display));
  }

  private void reportWith(Reporter<Employer> reporter)
  {
    employers.reportWith(reporter);
  }
}
