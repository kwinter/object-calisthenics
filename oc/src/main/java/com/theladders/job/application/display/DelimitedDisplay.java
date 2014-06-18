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
  public void write(String string)
  {
    builder.append(string);
  }

  @Override
  public void writeSeparator()
  {
    builder.append(delimiter);
  }

  @Override
  public void newline()
  {
    builder.append(NEW_LINE);
  }

  @Override
  public void write(Date date)
  {
    String dateString = SimpleDateFormat.getDateInstance().format(date);
    write(dateString);
  }

  @Override
  public void write(int number)
  {
    builder.append(number);
  }

  public String result()
  {
    return builder.toString();
  }

}
