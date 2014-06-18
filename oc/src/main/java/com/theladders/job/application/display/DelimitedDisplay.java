package com.theladders.job.application.display;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DelimitedDisplay implements Display
{
  public static final String  NEW_LINE = "\n";
  private final String        delimiter;
  private final StringBuilder builder  = new StringBuilder();

  public DelimitedDisplay(String delimiter)
  {
    this.delimiter = delimiter;
  }

  @Override
  public void writeEmployerName(String name)
  {
    write(name);
  }

  @Override
  public void writeJobTitle(String title)
  {
    write(title);
  }

  @Override
  public void writeJobseekerName(String name)
  {
    write(name);
  }

  @Override
  public void writeResumeTitle(String title)
  {
    write(title);
  }

  private void write(String string)
  {
    builder.append(string);
  }

  @Override
  public void writeSeparator()
  {
    builder.append(delimiter);
  }



  @Override
  public void writeApplicationDate(Date date)
  {
    String dateString = SimpleDateFormat.getDateInstance().format(date);
    write(dateString);
  }

  @Override
  public void writeNumberOfApplications(int number)
  {
    builder.append(number);
  }

  public String result()
  {
    return builder.toString();
  }

  @Override
  public void startSection()
  {
    // do nothing
  }

  @Override
  public void endSection()
  {
    // do nothing
  }

  @Override
  public void startRow()
  {
    // do nothing
  }

  @Override
  public void endRow()
  {
    newline();
  }

  private void newline()
  {
    builder.append(NEW_LINE);
  }
}
