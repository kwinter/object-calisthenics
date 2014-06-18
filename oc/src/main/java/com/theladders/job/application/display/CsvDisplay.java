package com.theladders.job.application.display;

public class CsvDisplay extends DelimitedDisplay
{
  public static final String DELIMITER = ",";

  public CsvDisplay()
  {
    super(DELIMITER);
  }

}
