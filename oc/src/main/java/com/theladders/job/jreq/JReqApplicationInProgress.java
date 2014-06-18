package com.theladders.job.jreq;

import com.theladders.jobseeker.Jobseeker;
import com.theladders.jobseeker.resume.Resume.ValidResume;

public class JReqApplicationInProgress
{
  private final Jobseeker jobseeker;
  private final JReq      job;

  public JReqApplicationInProgress(Jobseeker jobseeker,
                                   JReq job)
  {
    this.jobseeker = jobseeker;
    this.job = job;
  }

  public void with(ValidResume resume)
  {
    resume.verifyOwnerIs(jobseeker);
    job.submitApplicationFor(jobseeker, resume);
    // TODO (kw): is there a better way to do this while maintaining a fluent API?
    jobseeker.addToAppliedJobs(job);
  }

}
