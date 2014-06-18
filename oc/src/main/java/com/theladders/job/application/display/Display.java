package com.theladders.job.application.display;

import java.util.Date;

public interface Display
{
  void writeEmployerName(String name);

  void writeJobTitle(String title);

  void writeJobseekerName(String name);

  void writeResumeTitle(String title);

  void writeApplicationDate(Date date);

  void writeSeparator();

  void newline();

  void writeNumberOfApplications(int numberOfApplications);

  void startSection();
  void endSection();

  void startRow();

  void endRow();
}
