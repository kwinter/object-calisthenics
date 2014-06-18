package com.theladders.job.application.display;

import java.util.Date;

public interface Display
{
  void write(String string);

  void writeSeparator();

  void newline();

  void write(Date date);

  void write(int numberOfApplications);
}
