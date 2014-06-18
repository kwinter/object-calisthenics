package com.theladders.reporting.format;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.theladders.reporting.Display;

public class HtmlDisplay implements Display
{
  private final StringBuilder builder  = new StringBuilder();

  @Override
  public void writeEmployerName(String name)
  {
    writeColumn(name);
  }

  @Override
  public void writeJobTitle(String title)
  {
    writeColumn(title);
  }

  @Override
  public void writeJobseekerName(String name)
  {
    writeColumn(name);
  }

  @Override
  public void writeResumeTitle(String title)
  {
    writeColumn(title);
  }

  private void writeColumn(String string)
  {
    append("<td>" + string + "</td>");
  }

  @Override
  public void writeSeparator()
  {
    // do nothing
  }

  @Override
  public void writeApplicationDate(Date date)
  {
    String dateString = SimpleDateFormat.getDateInstance().format(date);
    writeColumn(dateString);
  }

  @Override
  public void writeNumberOfApplications(int number)
  {
    writeColumn(String.valueOf(number));
  }

  public String result()
  {
    return builder.toString();
  }

  @Override
  public void startSection()
  {
    append("<table>");
  }

  @Override
  public void endSection()
  {
    append("</table>");
  }

  @Override
  public void startRow()
  {
    append("<tr>");
  }

  @Override
  public void endRow()
  {
    append("</tr>");
  }

  private void append(String string)
  {
    builder.append(string);
  }
}
