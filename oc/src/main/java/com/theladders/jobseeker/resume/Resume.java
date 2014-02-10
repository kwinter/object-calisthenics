package com.theladders.jobseeker.resume;

import com.theladders.job.application.display.Display;

public interface Resume
{

  void displayOn(Display display);

  public static class ValidResume implements Resume
  {
    private final Title title;

    public ValidResume(Title title)
    {
      this.title = title;
    }

    @Override
    public void displayOn(Display display)
    {
      title.displayOn(display);
    }
  }

  public static class NoResume implements Resume
  {
    @Override
    public void displayOn(Display display)
    {}
  }

}
