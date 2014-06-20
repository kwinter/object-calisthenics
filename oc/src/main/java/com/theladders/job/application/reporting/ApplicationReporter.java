package com.theladders.job.application.reporting;

import java.util.Date;

import com.theladders.job.application.Applicant;

public interface ApplicationReporter
{
  void report(Applicant applicant,
              Date date);

}
