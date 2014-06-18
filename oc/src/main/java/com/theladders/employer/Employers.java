package com.theladders.employer;

import java.util.ArrayList;
import java.util.Collection;

import com.theladders.reporting.Reporter;

public class Employers
{
  private final Collection<Employer> employers = new ArrayList<>();

  public void add(Employer employer)
  {
    employers.add(employer);
  }

  public void reportWith(Reporter<Employer> reporter)
  {
    for (Employer employer : employers)
    {
      reporter.report(employer);
    }
  }
}
