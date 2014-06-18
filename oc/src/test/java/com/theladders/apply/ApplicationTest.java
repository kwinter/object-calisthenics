package com.theladders.apply;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.theladders.employer.Employer;
import com.theladders.job.application.NotYourResume;
import com.theladders.job.application.display.StringWriterDisplay;
import com.theladders.job.ats.AtsJob;
import com.theladders.job.jreq.JReq;
import com.theladders.jobseeker.Jobseeker;
import com.theladders.jobseeker.Name;
import com.theladders.jobseeker.resume.Resume.ValidResume;
import com.theladders.jobseeker.resume.Title;

public class ApplicationTest
{
  private static final String JOHNNYS_RESUME                     = "Johnny's resume";
  private static final String JOHNNYS_NAME                       = "Johnny";
  private static final String JOHNNYS_APPLICATION_WITH_RESUME    = applicationFor(today(), JOHNNYS_NAME, JOHNNYS_RESUME);
  private static final String JOHNNYS_APPLICATION_WITHOUT_RESUME = applicationFor(today(), JOHNNYS_NAME, "");

  @Test
  public void applyingToJReq()
  {
    Jobseeker jobseeker = new Jobseeker(new Name(JOHNNYS_NAME));
    Employer employer = new Employer(new com.theladders.employer.Name("Employer 1"));
    JReq job = employer.createJreqWith(new com.theladders.job.Title("Sweet job"));

    ValidResume resume = jobseeker.createResumeWith(new Title(JOHNNYS_RESUME));
    jobseeker.applyTo(job).with(resume);

    StringWriterDisplay display = new StringWriterDisplay();
    job.displayApplicationsOn(display);
    assertEquals(JOHNNYS_APPLICATION_WITH_RESUME, display.result());
  }

  @Test
  public void applyingToAtsJob()
  {
    Jobseeker jobseeker = new Jobseeker(new Name(JOHNNYS_NAME));
    Employer employer = new Employer(new com.theladders.employer.Name("Employer 1"));
    AtsJob job = employer.createAtsJobWith(new com.theladders.job.Title("Sweet job"));

    jobseeker.applyTo(job);

    StringWriterDisplay display = new StringWriterDisplay();
    job.displayApplicationsOn(display);
    assertEquals(JOHNNYS_APPLICATION_WITHOUT_RESUME, display.result());
  }

  @Test
  public void jobseekersCanListSavedJobs()
  {
    Jobseeker jobseeker = new Jobseeker(new Name(JOHNNYS_NAME));
    Employer employer = new Employer(new com.theladders.employer.Name("Employer 1"));
    AtsJob job = employer.createAtsJobWith(new com.theladders.job.Title("Sweet job"));

    jobseeker.save(job);

    StringWriterDisplay display = new StringWriterDisplay();
    jobseeker.displaySavedJobsOn(display);
    assertEquals(jobsDisplayFor(jobDisplayFor("Employer 1", "Sweet job")), display.result());
  }

  @Test
  public void jobseekersCanListAppliedJobs()
  {
    Jobseeker jobseeker = new Jobseeker(new Name(JOHNNYS_NAME));
    Employer employer = new Employer(new com.theladders.employer.Name("Employer 1"));
    JReq jreq = employer.createJreqWith(new com.theladders.job.Title("JReq"));
    employer.post(jreq);
    AtsJob atsJob = employer.createAtsJobWith(new com.theladders.job.Title("ATS job"));
    employer.post(atsJob);

    ValidResume resume = jobseeker.createResumeWith(new Title(JOHNNYS_RESUME));

    jobseeker.applyTo(jreq).with(resume);
    jobseeker.applyTo(atsJob);

    StringWriterDisplay display = new StringWriterDisplay();
    jobseeker.displayAppliedJobsOn(display);
    assertEquals(jobsDisplayFor(jobDisplayFor("Employer 1", "JReq"), jobDisplayFor("Employer 1", "ATS job")),
                 display.result());
  }

  @Test
  public void employersCanListJobs()
  {
    Employer employer = new Employer(new com.theladders.employer.Name("Employer 1"));

    JReq jreq = employer.createJreqWith(new com.theladders.job.Title("JReq"));
    employer.post(jreq);
    AtsJob atsJob = employer.createAtsJobWith(new com.theladders.job.Title("ATS job"));
    employer.post(atsJob);

    StringWriterDisplay display = new StringWriterDisplay();
    employer.displayJobsOn(display);
    assertEquals(jobsDisplayFor(jobDisplayFor("Employer 1", "JReq"), jobDisplayFor("Employer 1", "ATS job")),
                 display.result());
  }

  @Test(expected = NotYourResume.class)
  public void cantApplyWithSomeoneElsesResume()
  {
    Jobseeker jobseeker = new Jobseeker(new Name(JOHNNYS_NAME));
    Jobseeker anotherJobseeker = new Jobseeker(new Name("Bobby"));
    Employer employer = new Employer(new com.theladders.employer.Name("Employer 1"));
    JReq job = employer.createJreqWith(new com.theladders.job.Title("Sweet job"));

    ValidResume anotherJobseekersResume = anotherJobseeker.createResumeWith(new Title("another jobseeker's resume"));
    jobseeker.applyTo(job).with(anotherJobseekersResume);
  }

  @Test(expected = NotYourResume.class)
  // this is verifying we can handle jobseekers with the same name by checking that it can
  // distinguish between their resumes
  public void canHandleJobseekersWithTheSameName()
  {
    Jobseeker johnny = new Jobseeker(new Name(JOHNNYS_NAME));
    Jobseeker anotherJohnny = new Jobseeker(new Name(JOHNNYS_NAME));
    Employer employer = new Employer(new com.theladders.employer.Name("Employer 1"));
    JReq job = employer.createJreqWith(new com.theladders.job.Title("Sweet job"));

    ValidResume anotherJohnnysResume = anotherJohnny.createResumeWith(new Title("another jobseeker's resume"));
    johnny.applyTo(job).with(anotherJohnnysResume);
  }

  private static String applicationFor(Date date,
                                       String name,
                                       String resumeTitle)
  {
    return SimpleDateFormat.getDateInstance().format(date) + StringWriterDisplay.DELIMITER
           + name
           + StringWriterDisplay.DELIMITER
           + resumeTitle
           + StringWriterDisplay.NEW_LINE;
  }

  private static String jobsDisplayFor(String... jobs)
  {
    StringBuilder builder = new StringBuilder();
    for (String job : jobs)
    {
      builder.append(job).append(StringWriterDisplay.NEW_LINE);
    }
    return builder.toString();
  }

  private static String jobDisplayFor(String employer,
                                      String title)
  {
    return employer + StringWriterDisplay.DELIMITER + title;
  }

  private static Date today() {
    return new Date();
  }
}
