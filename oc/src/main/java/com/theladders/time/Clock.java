package com.theladders.time;

import java.util.Date;

public abstract class Clock
{
  private static Clock defaultClock = new SystemClock();

  protected abstract Date rightNow();

  public static Date now()
  {
    return defaultClock.rightNow();
  }

  public static void setDefaultTo(Clock clock)
  {
    defaultClock = clock;
  }

  public static class MockClock extends Clock
  {
    private Date date;

    public MockClock(Date date)
    {
      this.date = date;
    }

    @Override
    public Date rightNow()
    {
      if (date == null)
      {
        throw new RuntimeException("Set the date on MockClock first");
      }
      return date;
    }

    public void setTimeTo(Date newDate)
    {
      date = newDate;
    }
  }

  private static class SystemClock extends Clock
  {
    @Override
    public Date rightNow()
    {
      return new Date();
    }
  }
}
