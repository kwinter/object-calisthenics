package com.theladders;

import java.util.Date;

import com.theladders.employer.Employer;
import com.theladders.employer.Employers;
import com.theladders.employer.Name;
import com.theladders.reporting.Display;
import com.theladders.reporting.Reporter;
import com.theladders.reporting.application.bydate.ApplicantsByDate;
import com.theladders.reporting.application.bydate.DisplayApplicant;
import com.theladders.reporting.application.bydate.DisplayApplicants;
import com.theladders.reporting.application.byemployer.ApplicationsByEmployer;
import com.theladders.reporting.application.byjob.ApplicationsByJob;
import com.theladders.reporting.format.StringWriterDisplay;

// TODO(kw): starting and ending sections probably shouldn't be here
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

  public void reportJobseekersThatAppliedOn(Date date,
                                            StringWriterDisplay display)
  {
    reportWith(new ApplicantsByDate(new DisplayApplicants(new DisplayApplicant(date, display))));
  }

  private void reportWith(Reporter<Employer> reporter)
  {
    employers.reportWith(reporter);
  }


}
