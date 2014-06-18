package com.theladders.job.application.display;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringWriterDisplay implements Display
{
  public static final String  DELIMITER = "\t|\t";
  public static final Object  NEW_LINE  = "\n";
  private final StringBuilder builder   = new StringBuilder();

  @Override
  public void write(String string)
  {
    builder.append(string);
  }

  @Override
  public void writeSeparator()
  {
    builder.append(DELIMITER);
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
