package com.theladders.jobseeker.resume;

import com.theladders.job.application.NotYourResume;
import com.theladders.job.application.display.Display;
import com.theladders.jobseeker.Jobseeker;

public interface Resume
{

  void displayOn(Display display);

  public static class ValidResume implements Resume
  {
    private final Jobseeker jobseeker;
    private final Title title;

    public ValidResume(Jobseeker jobseeker,
                       Title title)
    {
      this.jobseeker = jobseeker;
      this.title = title;
    }

    @Override
    public void displayOn(Display display)
    {
      title.displayOn(display);
    }

    public void verifyOwnerIs(Jobseeker requester)
    {
      if (!jobseeker.equals(requester))
      {
        throw new NotYourResume();
      }
    }
  }

  public static class NoResume implements Resume
  {
    @Override
    public void displayOn(Display display)
    {}
  }

}
