package com.theladders.reporting.format;

public class CsvDisplay extends DelimitedDisplay
{
  public static final String DELIMITER = ",";

  public CsvDisplay()
  {
    super(DELIMITER);
  }

}
